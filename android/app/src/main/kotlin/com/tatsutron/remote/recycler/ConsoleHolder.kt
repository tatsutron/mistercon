package com.tatsutron.remote.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.FragmentMaker
import com.tatsutron.remote.Navigator
import com.tatsutron.remote.R

class ConsoleHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: ConsoleItem) {
        label.text = item.console.displayName
        itemView.setOnClickListener {
            Navigator.show(FragmentMaker.console(item.console.name))
        }
    }
}
