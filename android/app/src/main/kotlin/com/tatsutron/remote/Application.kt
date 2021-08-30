package com.tatsutron.remote

import java.io.File
import java.io.IOException

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        getExternalFilesDir(null)?.let {
            val dir = File("${it.absolutePath}/logs")
            if (!dir.exists()) {
                dir.mkdir()
            }
            val file = File(dir, "log_${System.currentTimeMillis()}.txt")
            try {
                Runtime.getRuntime().apply {
                    exec("logcat -c")
                    exec("logcat -f $file")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}