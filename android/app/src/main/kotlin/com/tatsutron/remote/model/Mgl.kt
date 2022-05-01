package com.tatsutron.remote.model

class Mgl(
    val delay: String,
    val index: String,
    // TODO Remove this hack when the MGL v. TGFX16-CD conflict is resolved
    val prefix: String = "",
    val rbf: String,
    val type: String,
)
