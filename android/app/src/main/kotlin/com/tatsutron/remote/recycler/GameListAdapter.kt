package com.tatsutron.remote.recycler

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.R

class GameListAdapter(
    private val activity: Activity,
    val itemList: MutableList<GameItem> = mutableListOf(),
) : RecyclerView.Adapter<GameHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GameHolder {
        val layoutInflater = LayoutInflater.from(activity)
        val itemView = layoutInflater.inflate(
            R.layout.item_game,
            parent,
            false, // attachToRoot
        )
        return GameHolder(activity, itemView)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(item = itemList[position])
    }

    override fun getItemCount() = itemList.size
}