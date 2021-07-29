package com.tatsutron.remote

import com.tatsutron.remote.data.Regions
import com.tatsutron.remote.data.Releases
import com.tatsutron.remote.data.Systems

class Game(
    val core: Core,
    val id: Long,
    val path: String,
    val region: Regions?,
    val release: Releases?,
    val system: Systems?,
)
