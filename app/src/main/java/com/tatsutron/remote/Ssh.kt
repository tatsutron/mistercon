package com.tatsutron.remote

import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.util.*

object Ssh {

    private const val HOST = "MiSTer"
    private const val PORT = 22
    private const val USER = "root"
    private const val PASSWORD = "1"
    private const val BUFFER_SIZE = 1024
    private const val SLEEP_MS = 300L

    private val jsch = JSch()

    fun command(command: String): String {
        val session = session()
        val channel = channel(session, command)
        val output = read(channel)
        channel.disconnect()
        session.disconnect()
        return output
    }

    private fun session(): Session =
        jsch.getSession(USER, HOST, PORT).apply {
            setConfig(
                Properties().apply {
                    setProperty("StrictHostKeyChecking", "no")
                }
            )
            setPassword(PASSWORD)
            connect()
        }

    private fun channel(session: Session, command: String) =
        (session.openChannel("exec") as ChannelExec).apply {
            inputStream = null
            setErrStream(System.err)
            setCommand(command)
            connect()
        }

    private fun read(channel: Channel): String {
        val inputStream = channel.inputStream
        val output = StringBuilder()
        val buffer = ByteArray(BUFFER_SIZE)
        while (true) {
            while (inputStream.available() > 0) {
                val bytesRead = inputStream.read(buffer, 0, BUFFER_SIZE)
                if (bytesRead < 0) {
                    break
                }
                output.append(String(buffer, 0, bytesRead))
            }
            if (channel.isClosed) {
                break
            }
            Thread.sleep(SLEEP_MS)
        }
        return output.toString()
    }
}
