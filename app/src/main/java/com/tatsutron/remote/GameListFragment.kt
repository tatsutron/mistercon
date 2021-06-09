package com.tatsutron.remote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File

class GameListFragment : Fragment(), CoroutineScope by MainScope() {
    private lateinit var adapter: GameListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_game_list,
            container,
            false, // attachToRoot
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GameListAdapter(
            context = requireContext(),
            itemList = makeItemList(Persistence.getGameList()),
        )
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        Event.SYNC.subscribe {
            val games = Persistence.getGameList()
            adapter.itemList.clear()
            adapter.itemList.addAll(makeItemList(games))
            adapter.notifyDataSetChanged()
        }
    }

    private fun makeItemList(games: List<Game>): MutableList<GameListItem> {
        val list = mutableListOf<GameListItem>()
        val cores = mutableSetOf<String>()
            .apply {
                games.forEach {
                    add(it.core.toString())
                }
            }
            .sorted()
        cores.forEach { core ->
            val coreItem = GameListItem(
                label = core.capitalize(),
            ).apply {
                onClick = {
                    isExpanded = !isExpanded
                    adapter.itemList.clear()
                    adapter.itemList.addAll(
                        list.filter {
                            it.parent == null || it.parent.isExpanded
                        }
                    )
                    adapter.notifyDataSetChanged()
                }
            }
            list.add(coreItem)
            val gamesByCore = mutableListOf<Game>()
            list.addAll(games
                .sortedBy {
                    it.release?.releaseTitleName ?: File(it.path).name
                }
                .filter { game ->
                    game.core.toString() == core
                }
                .map { game ->
                    gamesByCore.add(game)
                    GameListItem(
                        label = game.release?.releaseTitleName
                            ?: File(game.path).name,
                        onClick = {
                            requireActivity().supportFragmentManager
                                .beginTransaction()
                                .setTransition(
                                    FragmentTransaction.TRANSIT_NONE,
                                )
                                .add(
                                    R.id.root,
                                    FragmentMaker.game(game.id),
                                )
                                .addToBackStack(null)
                                .commit()
                        },
                        parent = coreItem,
                    )
                })
            if (gamesByCore.size > 1) {
                list.add(
                    GameListItem(
                        label = "Random",
                        labelColor = R.color.primary_100,
                        onClick = {
                            val game = gamesByCore.random()
                            launch(Dispatchers.IO) {
                                runCatching {
                                    val session = Ssh.session()
                                    game.core.play(session, game)
                                    session.disconnect()
                                }
                            }
                        },
                        parent = coreItem,
                    )
                )
            }
        }
        return list
            .filter {
                it.parent == null || it.parent.isExpanded
            }
            .toMutableList()
    }
}
