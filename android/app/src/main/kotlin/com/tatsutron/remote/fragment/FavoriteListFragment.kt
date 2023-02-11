package com.tatsutron.remote.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.R
import com.tatsutron.remote.recycler.GameItem
import com.tatsutron.remote.recycler.GameListAdapter
import com.tatsutron.remote.util.Persistence

class FavoriteListFragment : BaseFragment() {

    private lateinit var adapter: GameListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_favorite_list,
            container,
            false,
        )
    }

    override fun onResume() {
        super.onResume()
        setRecycler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GameListAdapter(activity as Activity)
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@FavoriteListFragment.adapter
        }
        setRecycler()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecycler() {
        adapter.itemList.clear()
        adapter.itemList.addAll(
            Persistence.getGamesByFavorite().map {
                GameItem(it, markAsFavorite = true)
            },
        )
        adapter.notifyDataSetChanged()
    }
}
