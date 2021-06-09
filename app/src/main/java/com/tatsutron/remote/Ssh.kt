package com.tatsutron.remote

import android.content.Context
import com.jcraft.jsch.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

object Ssh {

    private const val HOST = "MiSTer"
    private const val PORT = 22
    private const val USER = "root"
    private const val PASSWORD = "1"
    private const val BUFFER_SIZE = 1024
    private const val SLEEP_MS = 300L

    private val jsch = JSch()

    fun install(context: Context, session: Session) {
        val file = File("${context.cacheDir}/mbc")
        val input = context.assets.open("mbc")
        val buffer = input.readBytes()
        input.close()
        val output = FileOutputStream(file)
        output.write(buffer)
        output.close()
        val channel = sftp(session)
        try {
            channel.mkdir("/media/fat/mistercon")
        } catch (exception: Throwable) {
        }
        channel.put(file.path, "/media/fat/mistercon/mbc")
        channel.disconnect()
    }

    fun command(session: Session, command: String): String {
        val channel = exec(session, command)
        val output = read(channel)
        channel.disconnect()
        return output
    }

    fun session(): Session =
        jsch.getSession(USER, HOST, PORT).apply {
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
