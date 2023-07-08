package com.tatsutron.remote.recycler

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.R
import com.tatsutron.remote.model.Platform
import com.tatsutron.remote.util.FragmentMaker
import com.tatsutron.remote.util.Navigator
import com.tatsutron.remote.util.Util

class GameHolder(
    private val activity: Activity,
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val icon: ImageView = itemView.findViewById(R.id.icon)
    private val label: TextView = itemView.findViewById(R.id.label)
    private val subscript: TextView = itemView.findViewById(R.id.subscript)

    fun bind(item: GameItem) {
        item.icon.let {
            icon.setImageDrawable(
                AppCompatResources.getDrawable(icon.context, it),
            )
        }
        label.text = item.game.name
        subscript.apply {
            text = item.game.platform.displayName
            setTextColor(
                ContextCompat.getColor(
                    context,
                    item.game.platform.subscriptColor,
                )
            )
        }
        itemView.setOnClickListener {
            Navigator.showScreen(
                activity as AppCompatActivity,
                FragmentMaker.game(item.game.path),
            )
        }
        itemView.setOnLongClickListener {
            Navigator.showLoadingScreen()
            Util.loadGame(
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
