package com.tatsutron.remote

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.jcraft.jsch.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class ConsoleFragment : Fragment(), CoroutineScope by MainScope() {
    private lateinit var core: Core
    private lateinit var adapter: GameListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_console, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_console,
            container,
            false, // attachToRoot
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        core = Core.valueOf(arguments?.getString(FragmentMaker.KEY_CORE)!!)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view.findViewById(R.id.toolbar))
            supportActionBar?.title = core.displayName
        }
        view.findViewById<TextInputLayout>(R.id.games_path).apply {
            setEndIconOnClickListener {
                val context = requireContext()
                val disableButton = {
                    isEnabled = false
                    setEndIconTintList(
                        ColorStateList.valueOf(
                            context.getColor(R.color.gray_700),
                        ),
                    )
                }
                val enableButton = {
                    setEndIconTintList(
                        ColorStateList.valueOf(
                            context.getColor(R.color.white),
                        ),
                    )
                    isEnabled = true
                }
                disableButton()
                sync(
                    onSuccess = {
                        refresh()
                        enableButton()
                    },
                    onFailure = { throwable ->
                        MaterialAlertDialogBuilder(context)
                            .setTitle(context.getString(R.string.sync_error))
                            .setMessage(throwable.toString())
                            .setPositiveButton(
                                context.getString(R.string.ok),
                            ) { _, _ ->
                                enableButton()
                            }
                            .show()
                    },
                )
            }
        }
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            this@ConsoleFragment.adapter = GameListAdapter(
                context = requireContext(),
            )
            adapter = this@ConsoleFragment.adapter
            refresh()
        }
    }

    private fun sync(
        onSuccess: () -> Unit,
        onFailure: (throwable: Throwable) -> Unit,
    ) {
        Persistence.clearGamesByCore(core.name)
        launch(Dispatchers.IO) {
            runCatching {
                val session = Ssh.session()
                install(session)
                val extensions = core.commandsByExtension
                    .map {
                        it.key
                    }
                    .reduce { acc, string ->
                        "$acc|$string"
                    }
                val regex = "($extensions)$"
                val command = StringBuilder().apply {
                    append("\"${Constants.SCAN_PATH}\"")
                    append(" ")
                    append("\"${File(Constants.GAMES_PATH, core.name).path}\"")
                    append(" ")
                    append("\"$regex\"")
                    append(" ")
                    append(core.headerSizeInBytes.toString())
                }.toString()
                val list = Ssh.command(session, command)
                list.split("\n")
                    .filter {
                        it.isNotBlank()
                    }
                    .forEach {
                        val (path, hash) = it.split("\t")
                        Persistence.saveGame(core.name, path, hash)
                    }
                session.disconnect()
            }.onSuccess {
                requireActivity().runOnUiThread(onSuccess)
            }.onFailure {
                requireActivity().runOnUiThread {
                    onFailure(it)
                }
            }
        }
    }

    private fun install(session: Session) {
        Ssh.sftp(session).apply {
            try {
                mkdir("/media/fat/mistercon")
            } catch (exception: Throwable) {
            }
            disconnect()
        }
        val context = requireContext()
        val file = File(File(context.cacheDir, "scan").path)
        val input = requireContext().assets.open("scan")
        val buffer = input.readBytes()
        input.close()
        FileOutputStream(file).apply {
            write(buffer)
            close()
        }
        Ssh.sftp(session).apply {
            put(file.path, File("/media/fat/mistercon", "scan").path)
            disconnect()
        }
    }

    private fun refresh() {
        val items = Persistence.getGamesByCore(core.name).map {
            GameItem(
                label = it.release?.releaseTitleName ?: File(it.path).name,
                onClick = {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_NONE)
                        .add(R.id.root, FragmentMaker.game(it.id))
                        .addToBackStack(null)
                        .commit()
                },
            )
        }
        adapter.itemList.clear()
        adapter.itemList.addAll(items)
        adapter.notifyDataSetChanged()
    }
}