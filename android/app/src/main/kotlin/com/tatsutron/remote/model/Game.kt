package com.tatsutron.remote.model

import java.io.File

class Game(
    val console: Console,
    val path: String,
    val sha1: String?,
) {

    val name: String
        get() = File(path).nameWithoutExtension
}
