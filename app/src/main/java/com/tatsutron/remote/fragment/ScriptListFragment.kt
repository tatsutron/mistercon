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
import java.io.File

class ScriptListFragment : Fragment() {
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
        setPathInput(view)
        setSyncButton(view)
        setRecycler(view)
        refresh()
    }

    private fun setToolbar(view: View) {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view.findViewById(R.id.toolbar))
            supportActionBar?.title = context?.getString(R.string.scripts)
        }
    }

    private fun setPathInput(view: View) {
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
    }

    private fun setSyncButton(view: View) {
        view.findViewById<TextInputLayout>(R.id.scripts_path_layout).apply {
            setEndIconOnClickListener {
                val context = requireContext()
                val disable = {
                    isEnabled = false
                    setEndIconTintList(
                        ColorStateList.valueOf(
                            context.getColor(R.color.gray_700),
                        ),
                    )
                }
                val enable = {
                    setEndIconTintList(
                        ColorStateList.valueOf(
                            context.getColor(R.color.white),
                        ),
                    )
                    isEnabled = true
                }
                disable()
                Persistence.clearScripts()
                Coroutine.launch(
                    activity = requireActivity(),
                    run = {
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
                    },
                    success = {
                        refresh()
                        enable()
                    },
                    failure = {
                        enable()
                    },
                )
            }
        }
    }

    private fun setRecycler(view: View) {
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            this@ScriptListFragment.adapter = ScriptListAdapter(
                context = requireContext(),
            )
            adapter = this@ScriptListFragment.adapter
        }
    }

    private fun refresh() {
        val items = Persistence.getScriptList().map {
            ScriptItem(
                label = File(it).nameWithoutExtension,
                onClick = {
                    val context = requireContext()
                    Dialog.confirm(
                        context = context,
                        messageId = R.string.confirm_run_script,
                        ok = {
                            Coroutine.launch(
                                activity = requireActivity(),
                                run = {
                                    val session = Ssh.session()
                                    Asset.put(context, session, "mbc")
                                    val mbc = Constants.MBC_PATH
                                    Ssh.command(
                                        session,
                                        "$mbc load_rom SCRIPT $it",
                                    )
                                    session.disconnect()
                                },
                            )
                        }
                    )
                },
            )
        }
        adapter.itemList.clear()
        adapter.itemList.addAll(items)
        adapter.notifyDataSetChanged()
    }
}
