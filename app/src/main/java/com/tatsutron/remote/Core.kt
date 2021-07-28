package com.tatsutron.remote

enum class Core(
    val commandsByExtension: Map<String, String>,
    val displayName: String,
    val headerSizeInBytes: Int = 0,
) {

    Gameboy(
        commandsByExtension = mapOf(
            Pair(".gb", "GAMEBOY"),
        ),
        displayName = "Game Boy",
    ),

    GBA(
        commandsByExtension = mapOf(
            Pair(".gba", "GBA"),
        ),
        displayName = "Game Boy Advance",
    ),

    Genesis(
        commandsByExtension = mapOf(
            Pair(".bin", "MEGADRIVE.BIN"),
            Pair(".gen", "GENESIS"),
            Pair(".md", "MEGADRIVE"),
        ),
        displayName = "Sega Genesis",
    ),

    NES(
        commandsByExtension = mapOf(
            Pair(".fds", "NES"),
            Pair(".nes", "NES"),
        ),
        displayName = "Nintendo Entertainment System",
        headerSizeInBytes = 16,
    ),

    SNES(
        commandsByExtension = mapOf(
            Pair(".sfc", "SNES"),
        ),
        displayName = "Super NES",
    ),

    TGFX16(
        commandsByExtension = mapOf(
            Pair(".pce", "TGFX16"),
        ),
        displayName = "TurboGrafx-16",
    )
}
