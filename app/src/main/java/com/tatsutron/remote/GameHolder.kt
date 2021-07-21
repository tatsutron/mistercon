package com.tatsutron.remote

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: GameItem) {
        label.text = item.label
        itemView.setOnClickListener { item.onClick?.invoke() }
    }
}