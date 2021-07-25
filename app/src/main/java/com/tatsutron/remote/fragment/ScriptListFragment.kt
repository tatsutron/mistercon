package com.tatsutron.remote.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tatsutron.remote.*
import com.tatsutron.remote.recycler.ScriptItem
import com.tatsutron.remote.recycler.ScriptListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File

class ScriptListFragment : Fragment(), CoroutineScope by MainScope() {
    private lateinit var adapter: ScriptListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_script_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_script_list,
            container,
            false, // attachToRoot
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view.findViewById(R.id.toolbar))
            supportActionBar?.title = context?.getString(R.string.scripts)
        }
        view.findViewById<TextInputEditText>(R.id.scripts_path_text).apply {
            setText(Persistence.getConfig()?.scriptsPath)
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                    Persistence.saveScriptsPath(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
        view.findViewById<TextInputLayout>(R.id.scripts_path_layout).apply {
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
                        ErrorDialog.show(
                            context = requireContext(),
                            throwable = throwable,
                            cb = { enableButton() },
                        )
                    },
                )
            }
        }
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            this@ScriptListFragment.adapter = ScriptListAdapter(
                context = requireContext(),
            )
            adapter = this@ScriptListFragment.adapter
            refresh()
        }
    }

    private fun sync(
        onSuccess: () -> Unit,
        onFailure: (throwable: Throwable) -> Unit,
    ) {
        Persistence.clearScripts()
        launch(Dispatchers.IO) {
            runCatching {
                val session = Ssh.session()
                Asset.put(requireContext(), session, "mbc")
                val scriptsPath = Persistence.getConfig()?.scriptsPath
                Ssh.command(session, "ls $scriptsPath")
                    .split("\n")
                    .filter {
                        it.endsWith(".sh")
                    }
                    .forEach {
                        Persistence.saveScript(it)
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

    private fun refresh() {
        val items = Persistence.getScriptList().map {
            ScriptItem(
                label = File(it).name,
                onClick = {
                    launch(Dispatchers.IO) {
                        runCatching {
                            val session = Ssh.session()
                            Ssh.command(
                                session,
                                "${Constants.MBC_PATH} load_rom SCRIPT $it",
                            )
                            session.disconnect()
                        }.onSuccess {
                            requireActivity().runOnUiThread {}
                        }.onFailure { throwable ->
                            requireActivity().runOnUiThread {
                                ErrorDialog.show(requireContext(), throwable)
                            }
                        }
                    }
                },
            )
        }
        adapter.itemList.clear()
        adapter.itemList.addAll(items)
        adapter.notifyDataSetChanged()
    }
}
