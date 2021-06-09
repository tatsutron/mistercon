package com.tatsutron.remote

import com.jcraft.jsch.*
import java.util.*

object Ssh {

    private const val PORT = 22
    private const val USER = "root"
    private const val PASSWORD = "1"
    private const val BUFFER_SIZE = 1024
    private const val SLEEP_MS = 300L

    private val jsch = JSch()

    fun session(): Session =
        jsch.getSession(USER, Persistence.getHost(), PORT).apply {
            setConfig(
                Properties().apply {
                    setProperty("StrictHostKeyChecking", "no")
                }
            )
            setPassword(PASSWORD)
            connect()
        }

    fun sftp(session: Session) =
        (session.openChannel("sftp") as ChannelSftp).apply {
            connect()
        }

    fun command(session: Session, command: String): String {
        val channel = exec(session, command)
        val output = read(channel)
        channel.disconnect()
        return output
    }

    private fun exec(session: Session, command: String) =
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
