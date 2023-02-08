package com.tatsutron.remote.recycler

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.R

class PlatformListAdapter(
    private val activity: Activity,
    val itemList: MutableList<PlatformItem> = mutableListOf(),
) : RecyclerView.Adapter<PlatformHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PlatformHolder {
        val layoutInflater = LayoutInflater.from(activity)
        val itemView = layoutInflater.inflate(
            R.layout.list_item_console,
            parent,
            false,
        )
        return PlatformHolder(activity, itemView)
    }

    override fun onBindViewHolder(holder: PlatformHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size
}