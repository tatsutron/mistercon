package com.tatsutron.remote.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leinardi.android.speeddial.SpeedDialView
import com.tatsutron.remote.*
import com.tatsutron.remote.model.Game
import com.tatsutron.remote.model.Platform
import com.tatsutron.remote.recycler.FolderItem
import com.tatsutron.remote.recycler.GameItem
import com.tatsutron.remote.recycler.GameListAdapter
import com.tatsutron.remote.util.*
import java.io.File

class ArcadeListFragment : BaseFragment() {

    private lateinit var currentFolder: String
    private lateinit var adapter: GameListAdapter
    private var searchTerm = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_search, menu)
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
            R.layout.fragment_arcade_list,
            container,
            false,
        )
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view?.findViewById(R.id.toolbar))
            supportActionBar?.title = context?.getString(R.string.arcades)
        }
        if (Persistence.getGamesByPlatform(Platform.ARCADE).isEmpty()) {
            onSync()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentFolder = Persistence.getConfig()?.arcadePath!!
        adapter = GameListAdapter(requireActivity())
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ArcadeListFragment.adapter
        }
        setRecycler()
        setSpeedDial()
    }

    override fun onBackPressed(): Boolean {
        val arcadePath = Persistence.getConfig()?.arcadePath!!
        return if (currentFolder.length > arcadePath.length) {
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
        Persistence.getGamesByPlatform(Platform.ARCADE)
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
        val config = Persistence.getConfig()!!
        var arcadePath = config.arcadePath
        Dialog.input(
            context = context,
            title = context.getString(
                R.string.sync_specific,
                context.getString(R.string.arcades),
            ),
            text = arcadePath,
            ok = { _, text ->
                arcadePath = text.toString()
                Persistence.saveConfig(
                    config.copy(arcadePath = arcadePath),
                )
                Navigator.showLoadingScreen()
                Persistence.clearGamesByPlatform(Platform.ARCADE)
                Coroutine.launch(
                    activity = requireActivity(),
                    run = {
                        val session = Ssh.session()
                        Assets.require(requireContext(), session, "list")
                        val command = StringBuilder().apply {
                            append("\"${Constants.LIST_PATH}\"")
                            append(" ")
                            append("\"${arcadePath}\"")
                            append(" ")
                            append("\"(mra)$\"")
                        }.toString()
                        Ssh.command(session, command)
                            .split("\n")
                            .filter {
                                it.endsWith(".mra")
                            }
                            .forEach {
                                Persistence.saveGame(
                                    path = it,
                                    platform = Platform.ARCADE,
                                    sha1 = null,
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
