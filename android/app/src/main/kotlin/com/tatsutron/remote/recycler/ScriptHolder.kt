package com.tatsutron.remote.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.tatsutron.remote.R
import com.tatsutron.remote.util.*
import java.io.File

class ScriptHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val icon: ImageView = itemView.findViewById(R.id.icon)
    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: ScriptItem) {
        icon.setImageDrawable(
            AppCompatResources.getDrawable(icon.context, R.drawable.ic_script),
        )
        label.text = File(item.path).nameWithoutExtension
        itemView.setOnClickListener {
            Dialog.confirm(
                context = item.activity,
                message = item.activity.getString(R.string.confirm_run_script),
                ok = {
                    Navigator.showLoadingScreen()
                    Coroutine.launch(
                        activity = item.activity,
                        run = {
                            val session = Ssh.session()
                            Assets.require(item.activity, session, "mbc")
                            val command = StringBuilder().apply {
                                append("\"${Constants.MBC_PATH}\"")
                                append(" ")
                                append("load_rom")
                                append(" ")
                                append("SCRIPT")
                                append(" ")
                                append("\"${item.path}\"")
                            }.toString()
                            Ssh.command(session, command)
                            session.disconnect()
                        },
                        finally = {
                            Navigator.hideLoadingScreen()
                        },
                    )
                }
            )
        }
    }
}
