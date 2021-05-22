package com.tatsutron.remote

class GameListItem(
    var isExpanded: Boolean = false,
    val label: String,
    val labelColor: Int? = null,
    var onClick: (() -> Unit)? = null,
    val parent: GameListItem? = null,
)
