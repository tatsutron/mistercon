package com.tatsutron.remote.util

import android.content.Context

object Util {

    fun listFiles(
        context: Context,
        extensions: List<String>,
        path: String,
        recurse: Boolean,
    ): List<String> {
        val session = Ssh.session()
        Assets.require(context, session, "mister_util.py")
        val command = StringBuilder().apply {
            if (recurse) {
                append("python3 ${Constants.MISTER_UTIL_PATH} list -r")
            } else {
                append("python3 ${Constants.MISTER_UTIL_PATH} list")
            }
            append(" ")
            val ext = extensions.reduce { acc, string ->
                "$acc|$string"
            }
            append("\"${path}\" \"${ext}\"")
        }.toString()
        val output = Ssh.command(session, command)
        session.disconnect()
        return output.split(";")
    }

    fun hashFile(context: Context, path: String): String {
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
