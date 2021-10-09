package com.tatsutron.remote

import android.app.Activity
import com.tatsutron.remote.model.Game
import com.tatsutron.remote.util.Assets
import com.tatsutron.remote.util.Constants
import com.tatsutron.remote.util.Coroutine
import com.tatsutron.remote.util.Ssh
import java.io.File
import java.io.IOException

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        getExternalFilesDir(null)?.let {
            val dir = File("${it.absolutePath}/logs")
            if (!dir.exists()) {
                dir.mkdir()
            }
            val file = File(dir, "log_${System.currentTimeMillis()}.txt")
            try {
                Runtime.getRuntime().apply {
                    exec("logcat -c")
                    exec("logcat -f $file")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {

        fun loadGame(activity: Activity, game: Game, callback: () -> Unit) {
            Coroutine.launch(
                activity = activity,
                run = {
                    val session = Ssh.session()
                    Assets.require(activity, session, "mbc")
                    val mbcCommand = game.console.formats
                        .find {
                            it.extension == File(game.path).extension
                        }
                        ?.mbcCommand!!
                    val command = StringBuilder().apply {
                        append("\"${Constants.MBC_PATH}\"")
                        append(" ")
                        append("load_rom")
                        append(" ")
                        append(mbcCommand)
                        append(" ")
                        append("\"${game.path}\"")
                    }.toString()
                    Ssh.command(session, command)
                    session.disconnect()
                },
                finally = callback,
            )
        }
    }
}
