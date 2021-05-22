package com.tatsutron.remote

import com.tatsutron.remote.data.Regions
import com.tatsutron.remote.data.Releases
import com.tatsutron.remote.data.Systems

class Game(
    val core: Core,
    val filename: String,
    val region: Regions?,
    val release: Releases?,
    val system: Systems?,
)
