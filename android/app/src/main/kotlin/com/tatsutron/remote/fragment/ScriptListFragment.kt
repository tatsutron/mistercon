package com.tatsutron.remote.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leinardi.android.speeddial.SpeedDialView
import com.tatsutron.remote.*
import com.tatsutron.remote.recycler.ScriptItem
import com.tatsutron.remote.recycler.ScriptListAdapter
import com.tatsutron.remote.util.*
import java.io.File

class ScriptListFragment : BaseFragment() {
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
        setToolbar(view)
        setRecycler(view)
        setSpeedDial(view)
        refresh()
    }

    private fun setToolbar(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            supportActionBar?.title = context?.getString(R.string.scripts)
        }
    }

    private fun setRecycler(view: View) {
        adapter = ScriptListAdapter(
            context = requireContext(),
        )
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ScriptListFragment.adapter
        }
    }

    private fun setSpeedDial(view: View) {
        view.findViewById<SpeedDialView>(R.id.speed_dial).apply {
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

    @SuppressLint("NotifyDataSetChanged")
    private fun refresh() {
        val items = Persistence.getScriptList().map {
            ScriptItem(
                activity = requireActivity(),
                path = it,
            )
        }
        adapter.itemList.clear()
        adapter.itemList.addAll(items)
        adapter.notifyDataSetChanged()
    }

    private fun onSync() {
        val context = requireContext()
        Dialog.input(
            context = context,
            title = context.getString(R.string.sync),
            text = Persistence.getConfig()?.scriptsPath ?: "",
            ok = { _, text ->
                Navigator.showLoadingScreen()
                Persistence.saveScriptsPath(text.toString())
                Persistence.clearScripts()
                Coroutine.launch(
                    activity = requireActivity(),
                    run = {
                        val session = Ssh.session()
                        Assets.require(requireContext(), session, "mbc")
                        val scriptsPath = Persistence.getConfig()?.scriptsPath
                        Ssh.command(session, "ls $scriptsPath")
                            .split("\n")
                            .filter {
                                it.endsWith(".sh")
                            }
                            .forEach {
                                Persistence.saveScript(
                                    File(scriptsPath, it).path,
                                )
                            }
                        session.disconnect()
                    },
                    success = {
                        refresh()
                    },
                    finally = {
                        Navigator.hideLoadingScreen()
                    },
                )
            },
        )
    }
}
