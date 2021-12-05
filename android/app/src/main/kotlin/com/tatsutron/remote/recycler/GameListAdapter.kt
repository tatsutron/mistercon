package com.tatsutron.remote.recycler

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.l4digital.fastscroll.FastScroller
import com.tatsutron.remote.R
import java.util.*

class GameListAdapter(
    private val activity: Activity,
    val itemList: MutableList<GameListItem> = mutableListOf(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    FastScroller.SectionIndexer {

    companion object {
        const val TYPE_FOLDER = 0
        const val TYPE_GAME = 1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = if (viewType == TYPE_FOLDER) {
        val layoutInflater = LayoutInflater.from(activity)
        val itemView = layoutInflater.inflate(
            R.layout.item_folder,
            parent,
            false, // attachToRoot
        )
        FolderHolder(itemView)
    } else {
        val layoutInflater = LayoutInflater.from(activity)
        val itemView = layoutInflater.inflate(
            R.layout.item_game,
            parent,
            false, // attachToRoot
        )
        GameHolder(activity, itemView)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (getItemViewType(position)) {
            TYPE_FOLDER -> (holder as? FolderHolder)
                ?.bind(itemList[position] as FolderItem)
            else -> (holder as? GameHolder)
                ?.bind(itemList[position] as GameItem)
        }
    }

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int) =
        when (itemList[position]) {
            is FolderItem -> TYPE_FOLDER
            else -> TYPE_GAME
        }

    override fun getSectionText(position: Int) =
        itemList[position].text
            .first().toString().toUpperCase(Locale.getDefault())
}
