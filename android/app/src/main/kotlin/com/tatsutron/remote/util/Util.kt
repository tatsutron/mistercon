package com.tatsutron.remote.util

import android.content.Context

object Util {

    fun scan(
        context: Context,
        extensions: List<String>,
        path: String,
        includeZip: Boolean,
    ): List<String> {
        val session = Ssh.session()
        Assets.require(context, session, "mister_util.py")
        val command = StringBuilder().apply {
            append("python3 ${Constants.MISTER_UTIL_PATH} scan")
            append(" ")
            val ext = extensions.reduce { acc, string ->
                "$acc|$string"
            }
            append("\"${path}\" \"${ext}\"")
            append(" ")
            if (includeZip) {
                append("--include-zip")
            } else {
                append("--no-zip")
            }
        }.toString()
        val output = Ssh.command(session, command)
        session.disconnect()
        return output.split(";")
    }

    fun hash(context: Context, path: String): String {
        val session = Ssh.session()
        Assets.require(context, session, "mister_util.py")
        val command = StringBuilder().apply {
            append("python3 ${Constants.MISTER_UTIL_PATH} hash")
            append(" ")
            append("\"${path}\"")
        }.toString()
        val output = Ssh.command(session, command)
        session.disconnect()
        return output
    }
}
