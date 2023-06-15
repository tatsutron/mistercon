package com.tatsutron.remote.util

object Util {

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
}
