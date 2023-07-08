package com.tatsutron.remote.recycler

import com.tatsutron.remote.model.Game

class GameItem(
    val icon: Int,
    val game: Game,
    val subscript: String,
) : GameListItem {

    override val text: String
        get() = game.name
}
