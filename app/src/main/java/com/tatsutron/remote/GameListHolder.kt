package com.tatsutron.remote

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameListHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val icon: ImageView = itemView.findViewById(R.id.icon)
    private val label: TextView = itemView.findViewById(R.id.label)
    private val indentation: View = itemView.findViewById(R.id.indentation)

    fun bind(item: GameListItem) {
        if (item.parent == null) {
            icon.setImageResource(
                if (item.isExpanded) {
                    R.drawable.ic_item_expanded
                } else {
                    R.drawable.ic_item_collapsed
                }
            )
            icon.visibility = View.VISIBLE
            indentation.visibility = View.GONE
        } else {
            icon.visibility = View.GONE
            indentation.visibility = View.VISIBLE
        }
        label.text = item.label
        label.setTextColor(
            label.context.getColor(item.labelColor ?: R.color.white)
        )
        itemView.setOnClickListener { item.onClick?.invoke() }
    }
}