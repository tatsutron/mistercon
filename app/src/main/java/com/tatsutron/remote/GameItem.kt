package com.tatsutron.remote

class GameItem(
    val label: String,
    var onClick: (() -> Unit)? = null,
)
