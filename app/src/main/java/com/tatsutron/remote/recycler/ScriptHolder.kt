package com.tatsutron.remote.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.*
import java.io.File

class ScriptHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val label: TextView = itemView.findViewById(R.id.label)

    fun bind(item: ScriptItem) {
        label.text = File(item.path).nameWithoutExtension
        itemView.setOnClickListener {
            Dialog.confirm(
                context = item.activity,
                message = item.activity.getString(R.string.confirm_run_script),
                ok = {
                    Coroutine.launch(
                        activity = item.activity,
                        run = {
                            val session = Ssh.session()
                            Asset.put(item.activity, session, "mbc")
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
                    )
                }
            )
        }
    }
}
