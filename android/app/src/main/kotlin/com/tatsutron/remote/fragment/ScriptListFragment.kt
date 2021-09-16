package com.tatsutron.remote.fragment

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leinardi.android.speeddial.SpeedDialView
import com.tatsutron.remote.*
import com.tatsutron.remote.recycler.ScriptItem
import com.tatsutron.remote.recycler.ScriptListAdapter
import com.tatsutron.remote.util.*
import java.io.File

class ScriptListFragment : Fragment() {
    private lateinit var adapter: ScriptListAdapter
    private lateinit var progressBar: ProgressBar

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
        progressBar = view.findViewById(R.id.progress_bar)
        setToolbar(view)
        setRecycler(view)
        setSpeedDial(view)
        refresh()
    }

    private fun setToolbar(view: View) {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view.findViewById(R.id.toolbar))
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
                progressBar.visibility = View.VISIBLE
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
                                Persistence.saveScript(File(scriptsPath, it).path)
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
}
