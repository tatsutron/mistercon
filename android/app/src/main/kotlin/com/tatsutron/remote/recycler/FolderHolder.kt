package com.tatsutron.remote.recycler

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.R

class FolderHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: FolderItem) {
        label.text = item.text
        itemView.setOnClickListener {
            item.onClick()
        }
    }
}
