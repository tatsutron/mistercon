package com.tatsutron.remote.recycler

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.R
import com.tatsutron.remote.util.FragmentMaker
import com.tatsutron.remote.util.Navigator
import com.tatsutron.remote.util.Persistence
import java.util.*

class ConsoleHolder(
    private val activity: Activity,
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val image: ImageView = itemView.findViewById(R.id.image)
    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: ConsoleItem) {
        image.setImageDrawable(
            AppCompatResources.getDrawable(
                image.context,
                image.resources.getIdentifier(
                    item.console.name.toLowerCase(Locale.getDefault()),
                    "drawable",
                    image.context.packageName,
                )
            )
        )
        label.text = item.console.displayName
        label.alpha = if (
            Persistence.getGamesByConsole(item.console).isNotEmpty()
        ) {
            1.0f
        } else {
            0.75f
        }
        itemView.setOnClickListener {
            Navigator.showScreen(
                activity as AppCompatActivity,
                FragmentMaker.console(item.console.name),
            )
        }
    }
}
