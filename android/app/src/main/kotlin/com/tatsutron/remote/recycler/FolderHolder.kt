package com.tatsutron.remote.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.R

class FolderHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val icon: ImageView = itemView.findViewById(R.id.icon)
    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: FolderItem) {
        icon.setImageDrawable(
            AppCompatResources.getDrawable(icon.context, R.drawable.ic_folder),
        )
        label.text = item.text
        itemView.setOnClickListener {
            item.onClick()
        }
    }
}
