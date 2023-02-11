package com.tatsutron.remote.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcraft.jsch.JSchException
import com.leinardi.android.speeddial.SpeedDialView
import com.tatsutron.remote.R
import com.tatsutron.remote.model.Platform
import com.tatsutron.remote.recycler.PlatformItem
import com.tatsutron.remote.recycler.PlatformListAdapter
import com.tatsutron.remote.util.*
import java.io.File

class PlatformListFragment : BaseFragment() {

    private lateinit var platformCategory: Platform.Category
    private lateinit var adapter: PlatformListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_empty, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_platform_list,
            container,
            false,
        )
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view?.findViewById(R.id.toolbar))
            supportActionBar?.title = platformCategory.name
        }
        if (Persistence.getPlatforms(platformCategory).isEmpty()) {
            onSync()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        platformCategory = Platform.Category.valueOf(
            arguments?.getString(FragmentMaker.KEY_PLATFORM_CATEGORY)!!,
        )
        adapter = PlatformListAdapter(activity as Activity)
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PlatformListFragment.adapter
        }
        setRecycler()
        setSpeedDial()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecycler() {
        val items = Persistence.getPlatforms(platformCategory)
            .map {
                PlatformItem(it)
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
        Navigator.showLoadingScreen()
        Persistence.clearPlatforms()
        val activity = requireActivity()
        Coroutine.launch(
            activity = activity,
            run = {
                val corePaths = Util.listFiles(
                    context = activity,
                    extensions = listOf("rbf"),
                    path = platformCategory.path,
                    recurse = false,
                )
                val category = arguments
                    ?.getString(FragmentMaker.KEY_PLATFORM_CATEGORY)!!
                corePaths.forEach {
                    val coreId = File(it).name
                        .split("_")
                        .firstOrNull()
                    Platform.values().forEach { platform ->
                        if (platform.category.name == category && platform.coreId == coreId) {
                            Persistence.savePlatform(platform)
                        }
                    }
                }
            },
            success = {
                setRecycler()
            },
            failure = { throwable ->
                when (throwable) {
                    is JSchException ->
                        if (Persistence.getConfig() == null) {
                            Dialog.enterIpAddress(
                                context = activity,
                                ipAddressSet = ::onSync,
                            )
                        } else {
                            Dialog.connectionFailed(
                                context = activity,
                                ipAddressSet = ::onSync,
                            )
                        }
                    else ->
                        Dialog.error(activity, throwable)
                }
            },
            finally = {
                Navigator.hideLoadingScreen()
            },
        )
    }
}
