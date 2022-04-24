package com.tatsutron.remote.util

import android.content.Context
import com.jcraft.jsch.Session
import java.io.File
import java.io.FileOutputStream

object Assets {

    fun require(context: Context, session: Session, name: String) {
        Ssh.sftp(session).apply {
            try {
                mkdir(Constants.MISTERCON_ROOT)
            } catch (e: Throwable) {
                e.printStackTrace()
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
            put(file.path, File(Constants.MISTERCON_ROOT, name).path)
            disconnect()
        }
    }
}
