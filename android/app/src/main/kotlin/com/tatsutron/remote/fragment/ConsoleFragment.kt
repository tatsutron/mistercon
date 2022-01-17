package com.tatsutron.remote.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.tatsutron.remote.*
import com.tatsutron.remote.model.Console
import com.tatsutron.remote.model.Game
import com.tatsutron.remote.recycler.FolderItem
import com.tatsutron.remote.recycler.GameItem
import com.tatsutron.remote.recycler.GameListAdapter
import com.tatsutron.remote.util.*
import java.io.File

class ConsoleFragment : BaseFragment() {

    private lateinit var console: Console
    private lateinit var currentFolder: String
    private lateinit var adapter: GameListAdapter
    private var searchTerm = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_console, menu)
        super.onCreateOptionsMenu(menu, inflater)
        (menu.getItem(0).actionView as? SearchView)?.apply {
            maxWidth = Integer.MAX_VALUE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String) = true
                override fun onQueryTextChange(newText: String): Boolean {
                    searchTerm = newText
                    setRecycler()
                    return true
                }
            })
        }
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
        console = Console.valueOf(
            arguments?.getString(FragmentMaker.KEY_CONSOLE)!!
        )
        currentFolder = Persistence.getGamesPath(console)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            supportActionBar?.title = console.displayName
        }
        adapter = GameListAdapter(activity as Activity)
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ConsoleFragment.adapter
        }
        setRecycler()
        setSpeedDial()
        if (Persistence.getGamesByConsole(console).isEmpty()) {
            onSync(automatic = true)
        }
    }

    override fun onBackPressed(): Boolean {
        val gamesPath = Persistence.getGamesPath(console)
        return if (currentFolder.length > gamesPath.length) {
            currentFolder = File(currentFolder).parent!!
            setRecycler()
            true
        } else {
            super.onBackPressed()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecycler() {
        val subfolder: Game.() -> String? = {
            val relativePath = path
                .removePrefix("$currentFolder${File.separator}")
            val tokens = relativePath.split(File.separator)
            if (tokens.size <= 1) {
                null
            } else {
                tokens[0]
            }
        }
        val games = mutableListOf<Game>()
        val folders = mutableSetOf<String>()
        Persistence.getGamesByConsole(console)
            .filter {
                it.path.startsWith(currentFolder)
            }
            .forEach {
                val folder = it.subfolder()
                if (folder != null) {
                    folders.add(folder)
                } else {
                    games.add(it)
                }
            }
        val folderItems = folders
            .sorted()
            .map {
                FolderItem(
                    name = it,
                    onClick = {
                        currentFolder = File(currentFolder, it).path
                        setRecycler()
                    },
                )
            }
        val gameItems = games
            .map {
                GameItem(it)
            }
        val items = folderItems + gameItems
        adapter.itemList.clear()
        if (searchTerm.isBlank()) {
            adapter.itemList.addAll(items)
        } else {
            items.forEach {
                if (it.text.contains(searchTerm, ignoreCase = true)) {
                    adapter.itemList.add(it)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun setSpeedDial() {
        val context = requireContext()
        val string = { id: Int ->
            context.getString(id)
        }
        val color = { id: Int ->
            ResourcesCompat.getColor(resources, id, context.theme)
        }
        view?.findViewById<SpeedDialView>(R.id.speed_dial)?.apply {
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
                    SpeedDialActionItem.Builder(
                        R.id.random,
                        R.drawable.ic_random,
                    )
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

    private fun onSync(automatic: Boolean = false) {
        val context = requireContext()
        Dialog.input(
            context = context,
            title = context.getString(R.string.sync),
            text = Persistence.getGamesPath(console),
            ok = { _, text ->
                Persistence.saveGamesPath(console, text.toString())
                Navigator.showLoadingScreen()
                Coroutine.launch(
                    activity = requireActivity(),
                    run = {
                        val session = Ssh.session()
                        Assets.require(requireContext(), session, "list")
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
                        val old = Persistence.getGamesByConsole(console)
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
                        setRecycler()
                        setSpeedDial()
                    },
                    finally = {
                        Navigator.hideLoadingScreen()
                    },
                )
            },
            cancel = {
                if (automatic) {
                    activity?.onBackPressed()
                }
            },
        )
    }

    private fun onRandom() {
        Navigator.showScreen(
            activity as AppCompatActivity,
            FragmentMaker.game(
                adapter.itemList
                    .filterIsInstance<GameItem>()
                    .random()
                    .game.path
            )
        )
    }
}
