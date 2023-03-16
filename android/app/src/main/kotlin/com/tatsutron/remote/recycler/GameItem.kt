package com.tatsutron.remote.recycler

import com.tatsutron.remote.model.Game

class GameItem(
    val game: Game,
    val icon: Int? = null,
) : GameListItem {

    override val text: String
        get() = game.name
}
