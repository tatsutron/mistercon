package com.tatsutron.remote

import android.app.Activity
import com.tatsutron.remote.model.Game
import com.tatsutron.remote.util.*
import java.io.File
import java.io.FileOutputStream
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

        fun deployAssets(activity: Activity, callback: () -> Unit) {
            Coroutine.launch(
                activity = activity,
                run = {
                    val session = Ssh.session()
                    Ssh.sftp(session).apply {
                        try {
                            mkdir(Constants.TATSUTRON_ROOT)
                            mkdir(Constants.MISTERCON_ROOT)
                            mkdir(Constants.MREXT_ROOT)
                            mkdir(File(Constants.MREXT_ROOT, "out").path)
                            listOf(
                                Pair("mbc", Constants.MISTERCON_ROOT),
                                Pair("mister_util.py", Constants.MISTERCON_ROOT),
                                Pair("contool", Constants.MREXT_ROOT),
                            ).forEach {
                                val (name, folder) = it
                                val file = File(File(activity.cacheDir, name).path)
                                val input = activity.assets.open(name)
                                val buffer = input.readBytes()
                                input.close()
                                FileOutputStream(file).apply {
                                    write(buffer)
                                    close()
                                }
                                put(file.path, File(folder, name).path)
                            }
                        } catch (e:Throwable) {
                            e.printStackTrace()
                        }
                        disconnect()
                    }
                    session.disconnect()
                },
                finally = callback,
            )
        }

        fun loadGame(activity: Activity, game: Game, callback: () -> Unit) {
            Coroutine.launch(
                activity = activity,
                run = {
                    val session = Ssh.session()
                    val mbcCommand = game.platform.formats
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
