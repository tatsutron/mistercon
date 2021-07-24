package com.tatsutron.remote.recycler

class GameItem(
    val label: String,
    var onClick: (() -> Unit)? = null,
)
