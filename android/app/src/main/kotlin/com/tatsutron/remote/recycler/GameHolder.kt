package com.tatsutron.remote.recycler

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.Application
import com.tatsutron.remote.R
import com.tatsutron.remote.util.FragmentMaker
import com.tatsutron.remote.util.Navigator

class GameHolder(
    private val activity: Activity,
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val icon: ImageView = itemView.findViewById(R.id.icon)
    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: GameItem) {
        item.icon?.let {
            icon.setImageDrawable(
                AppCompatResources.getDrawable(icon.context, it),
            )
        }
        label.text = item.game.name
        itemView.setOnClickListener {
            Navigator.showScreen(
                activity as AppCompatActivity,
                FragmentMaker.game(item.game.path),
            )
        }
        itemView.setOnLongClickListener {
            Navigator.showLoadingScreen()
            Application.loadGame(
                activity = activity,
                game = item.game,
                callback = {
                    Navigator.hideLoadingScreen()
                },
            )
            true
        }
    }
}
