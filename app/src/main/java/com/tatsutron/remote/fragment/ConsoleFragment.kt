package com.tatsutron.remote.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tatsutron.remote.*
import com.tatsutron.remote.recycler.GameItem
import com.tatsutron.remote.recycler.GameListAdapter
import java.io.File

class ConsoleFragment : Fragment() {
    private lateinit var core: Core
    private lateinit var adapter: GameListAdapter
    private lateinit var randomButton: Button

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
        core = Core.valueOf(arguments?.getString(FragmentMaker.KEY_CORE)!!)
        setToolbar(view)
        setPathInput(view)
        setSyncButton(view)
        setRecycler(view)
        setRandomButton(view)
        refresh()
    }

    private fun setToolbar(view: View) {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view.findViewById(R.id.toolbar))
            supportActionBar?.title = core.displayName
        }
    }

    private fun setPathInput(view: View) {
        view.findViewById<TextInputEditText>(R.id.games_path_text).apply {
            setText(Persistence.getGamesPath(core.name))
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
                    Persistence.saveGamesPath(core.name, s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun setSyncButton(view: View) {
        view.findViewById<TextInputLayout>(R.id.games_path_layout).apply {
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
                Persistence.clearGamesByCore(core.name)
                Coroutine.launch(
                    activity = requireActivity(),
                    run = {
                        val session = Ssh.session()
                        Asset.put(requireContext(), session, "scan")
                        val extensions = core.commandsByExtension
                            .map {
                                it.key
                            }
                            .reduce { acc, string ->
                                "$acc|$string"
                            }
                        val gamesPath = Constants.GAMES_PATH
                        val regex = "($extensions)$"
                        val command = StringBuilder().apply {
                            append("\"${Constants.SCAN_PATH}\"")
                            append(" ")
                            append("\"${File(gamesPath, core.name).path}\"")
                            append(" ")
                            append("\"$regex\"")
                            append(" ")
                            append(core.headerSizeInBytes.toString())
                        }.toString()
                        val list = Ssh.command(session, command)
                        list.split("\n")
                            .filter {
                                it.isNotBlank()
                            }
                            .forEach {
                                val (path, hash) = it.split("\t")
                                Persistence.saveGame(core.name, path, hash)
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
        adapter = GameListAdapter(
            context = requireContext(),
        )
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun setRandomButton(view: View) {
        randomButton = view.findViewById(R.id.random_button)
        randomButton.setOnClickListener {
            Navigator.show(
                FragmentMaker.game(adapter.itemList.random().game.id),
            )
        }
    }

    private fun refresh() {
        val items = Persistence.getGamesByCore(core.name).map {
            GameItem(it)
        }
        adapter.itemList.clear()
        adapter.itemList.addAll(items)
        adapter.notifyDataSetChanged()
        randomButton.visibility = if (items.count() > 1) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
