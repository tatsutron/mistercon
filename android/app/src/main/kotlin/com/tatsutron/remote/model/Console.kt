package com.tatsutron.remote.model

enum class Console(
    val displayName: String,
    val formats: List<Format>,
    val gamesFolderDefault: String,
) {

//    BALLY_ASTROCADE(
//        displayName = "Bally Astrocade",
//        formats = listOf(
//            Format(
//                extension = "bin",
//                mbcCommand = "ASTROCADE",
//            ),
//        ),
//        gamesFolderDefault = "Astrocade",
//    ),

    ATARI_2600(
        displayName = "Atari 2600",
        formats = listOf(
            Format(
                extension = "rom",
                mbcCommand = "ATARI2600",
            ),
        ),
        gamesFolderDefault = "Atari2600",
    ),

    ATARI_5200(
        displayName = "Atari 5200",
        formats = listOf(
            Format(
                extension = "rom",
                mbcCommand = "ATARI5200",
            ),
        ),
        gamesFolderDefault = "Atari5200",
    ),

    COLECOVISION(
        displayName = "ColecoVision",
        formats = listOf(
            Format(
                extension = "col",
                mbcCommand = "COLECO",
            ),
        ),
        gamesFolderDefault = "Coleco",
    ),

    FAMICOM_DISK_SYSTEM(
        displayName = "Famicom Disk System",
        formats = listOf(
            Format(
                extension = "fds",
                headerSizeInBytes = 16,
                mbcCommand = "NES",
            ),
        ),
        gamesFolderDefault = "NES",
    ),

    GAME_BOY(
        displayName = "Game Boy",
        formats = listOf(
            Format(
                extension = "gb",
                mbcCommand = "GAMEBOY",
            ),
        ),
        gamesFolderDefault = "Gameboy",
    ),

    GAME_BOY_ADVANCE(
        displayName = "Game Boy Advance",
        formats = listOf(
            Format(
                extension = "gba",
                mbcCommand = "GBA",
            ),
        ),
        gamesFolderDefault = "GBA",
    ),

    GAME_BOY_COLOR(
        displayName = "Game Boy Color",
        formats = listOf(
            Format(
                extension = "gbc",
                mbcCommand = "GAMEBOY.COL",
            ),
        ),
        gamesFolderDefault = "Gameboy",
    ),

    NEO_GEO(
        displayName = "Neo Geo",
        formats = listOf(
            Format(
                extension = "neo",
                mbcCommand = "NEOGEO",
            ),
        ),
        gamesFolderDefault = "NeoGeo",
    ),

    NINTENDO_ENTERTAINMENT_SYSTEM(
        displayName = "Nintendo Entertainment System",
        formats = listOf(
            Format(
                extension = "nes",
                headerSizeInBytes = 16,
                mbcCommand = "NES",
            ),
        ),
        gamesFolderDefault = "NES",
    ),

    ODYSSEY_2(
        displayName = "Odyssey 2",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "ODYSSEY2",
            ),
        ),
        gamesFolderDefault = "Odyssey2",
    ),

//    SEGA_CD(
//        displayName = "Sega CD",
//        formats = listOf(
//            Format(
//                extension = "chd",
//                mbcCommand = "MEGACD",
//            ),
//            Format(
//                extension = "cue",
//                mbcCommand = "MEGACD.CUE",
//            ),
//        ),
//        gamesFolderDefault = "MegaCD",
//    ),

    SEGA_GENESIS(
        displayName = "Sega Genesis",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "MEGADRIVE.BIN",
            ),
            Format(
                extension = "gen",
                mbcCommand = "GENESIS",
            ),
            Format(
                extension = "md",
                mbcCommand = "MEGADRIVE",
            ),
        ),
        gamesFolderDefault = "Genesis",
    ),

    SEGA_MASTER_SYSTEM(
        displayName = "Sega Master System",
        formats = listOf(
            Format(
                extension = "sms",
                mbcCommand = "SMS",
            ),
        ),
        gamesFolderDefault = "SMS",
    ),

    SG_1000(
        displayName = "SG-1000",
        formats = listOf(
            Format(
                extension = "sg",
                mbcCommand = "COLECO.SG",
            ),
        ),
        gamesFolderDefault = "Coleco",
    ),

    SUPER_GRAFX(
        displayName = "SuperGrafx",
        formats = listOf(
            Format(
                extension = "sgx",
                mbcCommand = "SUPERGRAFX",
            ),
        ),
        gamesFolderDefault = "TGFX16",
    ),

    SUPER_NINTENDO(
        displayName = "Super Nintendo",
        formats = listOf(
            Format(
                extension = "sfc",
                mbcCommand = "SNES",
            ),
            Format(
                extension = "smc",
                mbcCommand = "SNES",
            ),
        ),
        gamesFolderDefault = "SNES",
    ),

    TURBO_GRAFX_16(
        displayName = "TurboGrafx-16",
        formats = listOf(
            Format(
                extension = "pce",
                mbcCommand = "TGFX16",
            ),
        ),
        gamesFolderDefault = "TGFX16",
    ),

//    TURBO_GRAFX_CD(
//        displayName = "TurboGrafx-CD",
//        formats = listOf(
//            Format(
//                extension = "chd",
//                mbcCommand = "TG16FX-CD",
//            ),
//            Format(
//                extension = "cue",
//                mbcCommand = "TG16FX-CD.CUE",
//            ),
//        ),
//        gamesFolderDefault = "TGFX16-CD",
//    ),

    VECTREX(
        displayName = "Vectrex",
        formats = listOf(
            Format(
                extension = "ovr",
                mbcCommand = "VECTREX.OVR",
            ),
            Format(
                extension = "vec",
                mbcCommand = "VECTREX",
            ),
        ),
        gamesFolderDefault = "Vectrex",
    ),
}
