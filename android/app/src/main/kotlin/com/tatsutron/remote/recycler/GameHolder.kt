package com.tatsutron.remote.recycler

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.Application
import com.tatsutron.remote.R
import com.tatsutron.remote.util.Dialog
import com.tatsutron.remote.util.FragmentMaker
import com.tatsutron.remote.util.Navigator

class GameHolder(
    private val activity: Activity,
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: GameItem) {
        label.text = item.game.name
        label.alpha = if (item.game.sha1?.isNotBlank() == true) {
            1.0f
        } else {
            0.75f
        }
        itemView.setOnClickListener {
            Navigator.showScreen(
                activity as AppCompatActivity,
                FragmentMaker.game(item.game.path),
            )
        }
        itemView.setOnLongClickListener {
            Dialog.confirm(
                context = activity,
                message = activity.getString(
                    R.string.confirm_play_game,
                    item.game.name,
                ),
                ok = {
                    Navigator.showLoadingScreen()
                    Application.loadGame(
                        activity = activity,
                        game = item.game,
                        callback = {
                            Navigator.hideLoadingScreen()
                        }
                    )
                },
            )
            true
        }
    }
}
