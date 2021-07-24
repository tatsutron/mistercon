package com.tatsutron.remote

import android.content.Context
import com.jcraft.jsch.Session
import java.io.File
import java.io.FileOutputStream

object Asset {

    fun put(context: Context, session: Session, name: String) {
        Ssh.sftp(session).apply {
            try {
                mkdir(Constants.MISTERCON_PATH)
            } catch (exception: Throwable) {
            }
            disconnect()
        }
        val file = File(File(context.cacheDir, name).path)
        val input = context.assets.open(name)
        val buffer = input.readBytes()
        input.close()
        FileOutputStream(file).apply {
            write(buffer)
            close()
        }
        Ssh.sftp(session).apply {
            put(file.path, File(Constants.MISTERCON_PATH, name).path)
            disconnect()
        }
    }
}
