package com.tatsutron.remote.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.FragmentMaker
import com.tatsutron.remote.Navigator
import com.tatsutron.remote.R
import java.io.File

class GameHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: GameItem) {
        label.text = item.game.release?.releaseTitleName
            ?: File(item.game.path).nameWithoutExtension
        itemView.setOnClickListener {
            Navigator.show(FragmentMaker.game(item.game.id))
        }
    }
}
