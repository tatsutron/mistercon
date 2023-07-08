package com.tatsutron.remote.util

import android.app.Activity
import com.tatsutron.remote.model.Game
import java.io.File
import java.io.FileOutputStream

object Util {

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
                        mkdir(Constants.MREXT_OUTPUT_PATH)
                        listOf(
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

    fun scan(mrextId: String): List<String> {
        val session = Ssh.session()
        val command = StringBuilder().apply {
            append(Constants.MREXT_CONTOOL_PATH)
            append(" ")
            append("-filter")
            append(" ")
            append(mrextId)
            append(" ")
            append("-out")
            append(" ")
            append(Constants.MREXT_OUTPUT_PATH)
            append(" ")
            append("-quiet")
            append(" ")
            append("&&")
            append(" ")
            append("cat")
            append(" ")
            append("${Constants.MREXT_OUTPUT_PATH}/${mrextId}.txt")
        }.toString()
        val output = Ssh.command(session, command)
        session.disconnect()
        return output
            .split("\n")
            .filter {
                it.isNotEmpty()
            }
    }

    fun hash(
        path: String,
        headerSizeInBytes: Int,
    ): String {
        val session = Ssh.session()
        val command = StringBuilder().apply {
            append("python3 ${Constants.MISTER_UTIL_PATH} hash")
            append(" ")
            append("\"${path}\"")
            append(" ")
            append(headerSizeInBytes)
        }.toString()
        val output = Ssh.command(session, command)
        session.disconnect()
        return output
    }

    fun loadGame(activity: Activity, game: Game, callback: () -> Unit) {
        Coroutine.launch(
            activity = activity,
            run = {
                val session = Ssh.session()
                val command = StringBuilder().apply {
                    append(Constants.MREXT_CONTOOL_PATH)
                    append(" ")
                    append("-launch")
                    append(" ")
                    append("\"")
                    append(game.path)
                    append("\"")
                }.toString()
                Ssh.command(session, command)
                session.disconnect()
            },
            finally = callback,
        )
    }
}
