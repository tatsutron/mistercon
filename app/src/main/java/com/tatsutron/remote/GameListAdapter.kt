package com.tatsutron.remote

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GameListAdapter(
    private val context: Context,
    val itemList: MutableList<GameListItem> = mutableListOf(),
) : RecyclerView.Adapter<GameListHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GameListHolder {
        val layoutInflater = LayoutInflater.from(context)
        val itemView = layoutInflater.inflate(
            R.layout.item_game_list,
            parent,
            false, // attachToRoot
        )
        return GameListHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: GameListHolder, position: Int) =
        holder.bind(item = itemList[position])

    override fun getItemCount() = itemList.size
}