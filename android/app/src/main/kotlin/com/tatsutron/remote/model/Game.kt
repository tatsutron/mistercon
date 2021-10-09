package com.tatsutron.remote.model

import android.app.Activity
import com.tatsutron.remote.util.Assets
import com.tatsutron.remote.util.Constants
import com.tatsutron.remote.util.Coroutine
import com.tatsutron.remote.util.Ssh
import java.io.File

class Game(
    val console: Console,
    val path: String,
    val sha1: String?,
) {

    val name: String
        get() = File(path).nameWithoutExtension

    fun play(
        activity: Activity,
        callback: (() -> Unit)? = null,
    ) {
        Coroutine.launch(
            activity = activity,
            run = {
                val session = Ssh.session()
                Assets.require(activity, session, "mbc")
                val mbcCommand = console.formats
                    .find {
                        it.extension == File(path).extension
                    }
                    ?.mbcCommand!!
                val command = StringBuilder().apply {
                    append("\"${Constants.MBC_PATH}\"")
                    append(" ")
                    append("load_rom")
                    append(" ")
                    append(mbcCommand)
                    append(" ")
                    append("\"${path}\"")
                }.toString()
                Ssh.command(session, command)
                session.disconnect()
            },
            finally = callback,
        )
    }
}
