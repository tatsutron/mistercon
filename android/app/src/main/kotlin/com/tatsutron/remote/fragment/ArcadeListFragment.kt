package com.tatsutron.remote.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leinardi.android.speeddial.SpeedDialView
import com.tatsutron.remote.*
import com.tatsutron.remote.model.Console
import com.tatsutron.remote.recycler.GameItem
import com.tatsutron.remote.recycler.GameListAdapter
import com.tatsutron.remote.util.*
import java.io.File

class ArcadeListFragment : BaseFragment() {

    private lateinit var adapter: GameListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_arcade_list,
            container,
            false, // attachToRoot
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GameListAdapter(requireActivity())
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ArcadeListFragment.adapter
        }
        setRecycler()
        setSpeedDial()
        if (Persistence.getGamesByConsole(Console.ARCADE).isEmpty()) {
            onSync()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecycler() {
        val items = Persistence.getGamesByConsole(Console.ARCADE)
            .map {
                GameItem(it)
            }
        adapter.itemList.clear()
        adapter.itemList.addAll(items)
        adapter.notifyDataSetChanged()
    }

    private fun setSpeedDial() {
        view?.findViewById<SpeedDialView>(R.id.speed_dial)?.apply {
            mainFab.apply {
                setOnClickListener {
                    onSync()
                }
                setImageDrawable(
                    AppCompatResources.getDrawable(context, R.drawable.ic_sync)
                )
            }
        }
    }

    private fun onSync() {
        val context = requireContext()
        // TODO Replace with `Persistence.getConfig()?.arcadePath ?: ""`
        val arcadePath = Constants.ARCADE_PATH
        Dialog.input(
            context = context,
            title = context.getString(R.string.sync),
            text = arcadePath,
            ok = { _, text ->
                Navigator.showLoadingScreen()
                Persistence.saveScriptsPath(text.toString())
                Persistence.clearScripts()
                Coroutine.launch(
                    activity = requireActivity(),
                    run = {
                        val session = Ssh.session()
                        Ssh.command(session, "ls $arcadePath")
                            .split("\n")
                            .filter {
                                it.endsWith(".mra")
                            }
                            .forEach {
                                Persistence.saveGame(
                                    core = Console.ARCADE.name,
                                    path = File(arcadePath, it).path,
                                    hash = null,
                                )
                            }
                        session.disconnect()
                    },
                    success = {
                        setRecycler()
                    },
                    finally = {
                        Navigator.hideLoadingScreen()
                    },
                )
            },
        )
    }
}
