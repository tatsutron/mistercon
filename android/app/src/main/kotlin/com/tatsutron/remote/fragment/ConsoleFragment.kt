package com.tatsutron.remote.fragment

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.tatsutron.remote.*
import com.tatsutron.remote.recycler.GameItem
import com.tatsutron.remote.recycler.GameListAdapter

class ConsoleFragment : Fragment() {
    private lateinit var console: Console
    private lateinit var toolbar: Toolbar
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: GameListAdapter
    private lateinit var speedDial: SpeedDialView
    private lateinit var progressBar: ProgressBar

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
        console = Console.valueOf(arguments?.getString(FragmentMaker.KEY_CONSOLE)!!)
        toolbar = view.findViewById(R.id.toolbar)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.title = console.displayName
        }
        recycler = view.findViewById(R.id.recycler)
        adapter = GameListAdapter(
            context = requireContext(),
        )
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ConsoleFragment.adapter
        }
        speedDial = view.findViewById(R.id.speed_dial)
        progressBar = view.findViewById(R.id.progress_bar)
        refresh()
    }

    private fun refresh() {
        refreshRecycler()
        refreshSpeedDial()
    }

    private fun refreshRecycler() {
        val items = Persistence.getGamesByConsole(console.name).map {
            GameItem(it)
        }
        adapter.itemList.clear()
        adapter.itemList.addAll(items)
        adapter.notifyDataSetChanged()
    }

    private fun refreshSpeedDial() {
        val context = requireContext()
        val string = { id: Int ->
            context.getString(id)
        }
        val color = { id: Int ->
            ResourcesCompat.getColor(resources, id, context.theme)
        }
        speedDial.apply {
            clearActionItems()
            addActionItem(
                SpeedDialActionItem.Builder(R.id.sync, R.drawable.ic_sync)
                    .setLabel(string(R.string.sync))
                    .setLabelBackgroundColor(color(R.color.gray_900))
                    .setLabelColor(color(R.color.primary_500))
                    .setFabBackgroundColor(color(R.color.gray_900))
                    .setFabImageTintColor(color(R.color.primary_500))
                    .create()
            )
            if (adapter.itemList.count() > 1) {
                addActionItem(
                    SpeedDialActionItem.Builder(R.id.random, R.drawable.ic_random)
                        .setLabel(string(R.string.random))
                        .setLabelBackgroundColor(color(R.color.gray_900))
                        .setLabelColor(color(R.color.primary_500))
                        .setFabBackgroundColor(color(R.color.gray_900))
                        .setFabImageTintColor(color(R.color.primary_500))
                        .create()
                )
            }
            setOnActionSelectedListener(
                SpeedDialView.OnActionSelectedListener { actionItem ->
                    when (actionItem.id) {
                        R.id.random -> {
                            onRandom()
                            close()
                            return@OnActionSelectedListener true
                        }
                        R.id.sync -> {
                            onSync()
                            close()
                            return@OnActionSelectedListener true
                        }
                    }
                    false
                }
            )
        }
    }

    private fun onSync() {
        val context = requireContext()
        Dialog.input(
            context = context,
            title = context.getString(R.string.sync),
            text = Persistence.getGamesPath(console),
            ok = { _, text ->
                Persistence.saveGamesPath(console, text.toString())
                progressBar.visibility = View.VISIBLE
                Coroutine.launch(
                    activity = requireActivity(),
                    run = {
                        val session = Ssh.session()
                        Asset.put(requireContext(), session, "list")
                        val gamesPath = Persistence.getGamesPath(console)
                        val extensions = console.formats
                            .map {
                                it.extension
                            }
                            .reduce { acc, string ->
                                "$acc|$string"
                            }
                        val regex = "($extensions)$"
                        val command = StringBuilder().apply {
                            append("\"${Constants.LIST_PATH}\"")
                            append(" ")
                            append("\"${gamesPath}\"")
                            append(" ")
                            append("\"$regex\"")
                        }.toString()
                        val list = Ssh.command(session, command)
                        val new = list.split("\n")
                            .filter {
                                it.isNotBlank()
                            }
                        val old = Persistence.getGamesByConsole(console.name)
                            .map {
                                it.path
                            }
                        new.forEach {
                            if (it !in old) {
                                Persistence.saveGame(
                                    core = console.name,
                                    path = it,
                                    hash = null,
                                )
                            }
                        }
                        old.forEach {
                            if (it !in new) {
                                Persistence.deleteGame(it)
                            }
                        }
                        session.disconnect()
                    },
                    success = {
                        refresh()
                        progressBar.visibility = View.GONE
                    },
                    failure = {
                        progressBar.visibility = View.GONE
                    },
                )
            },
        )
    }

    private fun onRandom() {
        Navigator.show(
            FragmentMaker.game(adapter.itemList.random().game.path),
        )
    }
}
