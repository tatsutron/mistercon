package com.tatsutron.remote.util

object Util {

    fun scan(
        path: String,
        extensions: List<String>,
        includeZip: Boolean,
    ): List<String> {
        val session = Ssh.session()
        val command = StringBuilder().apply {
            append("python3 ${Constants.MISTER_UTIL_PATH} scan")
            append(" ")
            val ext = extensions.reduce { acc, string ->
                "$acc|$string"
            }
            append("\"${path}\"")
            append(" ")
            append("\"${ext}\"")
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
}
