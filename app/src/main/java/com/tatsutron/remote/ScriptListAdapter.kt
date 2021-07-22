package com.tatsutron.remote

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ScriptListAdapter(
    private val context: Context,
    val itemList: MutableList<ScriptItem> = mutableListOf(),
) : RecyclerView.Adapter<ScriptHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ScriptHolder {
        val layoutInflater = LayoutInflater.from(context)
        val itemView = layoutInflater.inflate(
            R.layout.item_script,
            parent,
            false, // attachToRoot
        )
        return ScriptHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: ScriptHolder, position: Int) {
        holder.bind(item = itemList[position])
    }

    override fun getItemCount() = itemList.size
}