package com.tatsutron.remote.model

import java.io.File

class Game(
    val platform: Platform,
    val favorite: Boolean,
    val path: String,
    val sha1: String?,
) {

    val name: String
        get() = File(path).nameWithoutExtension
}
