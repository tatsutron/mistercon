package com.tatsutron.remote.recycler

class FolderItem(
    val name: String,
    val onClick: () -> Unit,
) : GameListItem {

    override val text: String
        get() = name
}
