package com.tatsutron.remote.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.R

class ConsoleHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: ConsoleItem) {
        label.text = item.label
        itemView.setOnClickListener { item.onClick?.invoke() }
    }
}
