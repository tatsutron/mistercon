package com.tatsutron.remote.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leinardi.android.speeddial.SpeedDialView
import com.tatsutron.remote.R
import com.tatsutron.remote.model.Platform
import com.tatsutron.remote.recycler.ConsoleItem
import com.tatsutron.remote.recycler.ConsoleListAdapter
import com.tatsutron.remote.util.*
import java.io.File

class ConsoleListFragment : BaseFragment() {

    private lateinit var adapter: ConsoleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_console_list,
            container,
            false, // attachToRoot
        )
    }

    override fun onResume() {
        super.onResume()
        if (Persistence.getConsolePlatforms().isEmpty()) {
            onSync()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ConsoleListAdapter(activity as Activity)
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ConsoleListFragment.adapter
        }
        setRecycler()
        setSpeedDial()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecycler() {
        val items = Persistence.getConsolePlatforms()
            .map {
                ConsoleItem(it)
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
        val config = Persistence.getConfig()!!
        var consolePath = config.consolePath
        Dialog.input(
            context = context,
            title = context.getString(
                R.string.sync_specific,
                context.getString(R.string.consoles),
            ),
            text = consolePath,
            ok = { _, text ->
                consolePath = text.toString()
                Persistence.saveConfig(
                    config.copy(consolePath = consolePath),
                )
                Navigator.showLoadingScreen()
                Persistence.clearPlatforms()
                Coroutine.launch(
                    activity = requireActivity(),
                    run = {
                        val session = Ssh.session()
                        Ssh.command(session, "ls $consolePath")
                            .split("\n")
                            .filter {
                                it.endsWith(".rbf")
                            }
                            .forEach {
                                val coreId = File(it).name
                                    .split("_")
                                    .firstOrNull()
                                Platform.values().forEach { platform ->
                                    if (platform.coreId == coreId) {
                                        Persistence.savePlatform(
                                            corePath =
                                            File(
                                                consolePath,
                                                it,
                                            ).path,
                                            gamesPath = Persistence
                                                .getPlatform(platform)
                                                ?.gamesPath
                                                ?: File(
                                                    Constants.GAMES_PATH,
                                                    platform
                                                        .gamesFolderDefault!!,
                                                ).path,
                                            platform = platform,
                                        )
                                    }
                                }
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
