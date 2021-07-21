package com.tatsutron.remote

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GameListAdapter(
    private val context: Context,
    val itemList: MutableList<GameItem> = mutableListOf(),
) : RecyclerView.Adapter<GameHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GameHolder {
        val layoutInflater = LayoutInflater.from(context)
        val itemView = layoutInflater.inflate(
            R.layout.item_game,
            parent,
            false, // attachToRoot
        )
        return GameHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(item = itemList[position])
    }

    override fun getItemCount() = itemList.size
}