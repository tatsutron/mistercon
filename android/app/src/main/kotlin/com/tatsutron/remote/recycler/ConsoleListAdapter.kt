package com.tatsutron.remote.recycler

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.R

class ConsoleListAdapter(
    private val activity: Activity,
    val itemList: MutableList<ConsoleItem> = mutableListOf(),
) : RecyclerView.Adapter<ConsoleHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ConsoleHolder {
        val layoutInflater = LayoutInflater.from(activity)
        val itemView = layoutInflater.inflate(
            R.layout.list_item_console,
            parent,
            false,
        )
        return ConsoleHolder(activity, itemView)
    }

    override fun onBindViewHolder(holder: ConsoleHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size
}