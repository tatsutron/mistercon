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
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            supportActionBar?.title = context?.getString(R.string.scripts)
        }
        adapter = ScriptListAdapter(
            context = requireContext(),
        )
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ScriptListFragment.adapter
        }
        setRecycler()
        setSpeedDial()
        if (Persistence.getScriptList().isEmpty()) {
            onSync(automatic = true)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecycler() {
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

    private fun onSync(automatic: Boolean = false) {
        val context = requireContext()
        val config = Persistence.getConfig()!!
        var scriptsPath = config.scriptsPath
        Dialog.input(
            context = context,
            title = context.getString(
                R.string.sync_specific,
                context.getString(R.string.scripts),
            ),
            text = scriptsPath,
            ok = { _, text ->
                scriptsPath = text.toString()
                Persistence.saveConfig(
                    config.copy(scriptsPath = scriptsPath),
                )
                Navigator.showLoadingScreen()
                Persistence.clearScripts()
                Coroutine.launch(
                    activity = requireActivity(),
                    run = {
                        val session = Ssh.session()
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
                        setRecycler()
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
}
