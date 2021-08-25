package com.tatsutron.remote.model

import android.app.Activity
import com.tatsutron.remote.*
import com.tatsutron.remote.data.Regions
import com.tatsutron.remote.data.Releases
import com.tatsutron.remote.data.Systems
import java.io.File

class Game(
    val core: Core,
    val path: String,
    val sha1: String?,
    val region: Regions?,
    val release: Releases?,
    val system: Systems?,
) {

    fun play(activity: Activity) {
        Coroutine.launch(
            activity = activity,
            run = {
                val session = Ssh.session()
                Asset.put(activity, session, "mbc")
                val extension = File(path).extension
                val command = StringBuilder().apply {
                    append("\"${Constants.MBC_PATH}\"")
                    append(" ")
                    append("load_rom")
                    append(" ")
                    append(core.commandsByExtension[extension])
                    append(" ")
                    append("\"${path}\"")
                }.toString()
                Ssh.command(session, command)
                session.disconnect()
            },
        )
    }
}
