package com.tatsutron.remote.recycler

import com.tatsutron.remote.model.Game

class GameItem(
    val game: Game,
) : GameListItem {

    override val text: String
        get() = game.name
}
