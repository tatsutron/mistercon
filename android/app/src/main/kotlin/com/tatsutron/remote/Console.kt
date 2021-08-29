package com.tatsutron.remote

enum class Console(
    val core: Core,
    val displayName: String,
    val formats: List<Format>,
) {

//    BALLY_ASTROCADE(
//        core = Core.Astrocade,
//        displayName = "Bally Astrocade",
//        formats = listOf(
//            Format(
//                extension = "bin",
//                mbcCommand = "ASTROCADE",
//            ),
//        ),
//    ),

    ATARI_2600(
        core = Core.Atari2600,
        displayName = "Atari 2600",
        formats = listOf(
            Format(
                extension = "rom",
                mbcCommand = "ATARI2600",
            ),
        ),
    ),

    ATARI_5200(
        core = Core.Atari5200,
        displayName = "Atari 5200",
        formats = listOf(
            Format(
                extension = "rom",
                mbcCommand = "ATARI5200",
            ),
        ),
    ),

    COLECOVISION(
        core = Core.ColecoVision,
        displayName = "ColecoVision",
        formats = listOf(
            Format(
                extension = "col",
                mbcCommand = "COLECO",
            ),
        ),
    ),

//    FAMICOM_DISK_SYSTEM(
//        core = Core.NES,
//        displayName = "Famicom Disk System",
//        formats = listOf(
//            Format(
//                extension = "fds",
//                headerSizeInBytes = 16,
//                mbcCommand = "NES.FDS",
//            ),
//        ),
//    ),

    GAME_BOY(
        core = Core.Gameboy,
        displayName = "Game Boy",
        formats = listOf(
            Format(
                extension = "gb",
                mbcCommand = "GAMEBOY",
            ),
        ),
    ),

    GAME_BOY_ADVANCE(
        core = Core.GBA,
        displayName = "Game Boy Advance",
        formats = listOf(
            Format(
                extension = "gba",
                mbcCommand = "GBA",
            ),
        ),
    ),

    GAME_BOY_COLOR(
        core = Core.Gameboy,
        displayName = "Game Boy Color",
        formats = listOf(
            Format(
                extension = "gbc",
                mbcCommand = "GAMEBOY.COL",
            ),
        ),
    ),

    NEO_GEO(
        core = Core.NeoGeo,
        displayName = "Neo Geo",
        formats = listOf(
            Format(
                extension = "neo",
                mbcCommand = "NEOGEO",
            ),
        ),
    ),

    NINTENDO_ENTERTAINMENT_SYSTEM(
        core = Core.NES,
        displayName = "Nintendo Entertainment System",
        formats = listOf(
            Format(
                extension = "nes",
                headerSizeInBytes = 16,
                mbcCommand = "NES",
            ),
        ),
    ),

    ODYSSEY_2(
        core = Core.Odyssey2,
        displayName = "Odyssey 2",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "ODYSSEY2",
            ),
        ),
    ),

//    SEGA_CD(
//        core = Core.MegaCD,
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
//    ),

    SEGA_GENESIS(
        core = Core.Genesis,
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
    ),

    SEGA_MASTER_SYSTEM(
        core = Core.SMS,
        displayName = "Sega Master System",
        formats = listOf(
            Format(
                extension = "sms",
                mbcCommand = "SMS",
            ),
        ),
    ),

    SG_1000(
        core = Core.ColecoVision,
        displayName = "SG-1000",
        formats = listOf(
            Format(
                extension = "sg",
                mbcCommand = "COLECO.SG",
            ),
        ),
    ),

    SUPER_GRAFX(
        core = Core.TGFX16,
        displayName = "SuperGrafx",
        formats = listOf(
            Format(
                extension = "sgx",
                mbcCommand = "SUPERGRAFX",
            ),
        ),
    ),

    SUPER_NINTENDO(
        core = Core.SNES,
        displayName = "Super Nintendo",
        formats = listOf(
            Format(
                extension = "sfc",
                mbcCommand = "SNES",
            ),
        ),
    ),

    TURBO_GRAFX_16(
        core = Core.TGFX16,
        displayName = "TurboGrafx-16",
        formats = listOf(
            Format(
                extension = "pce",
                mbcCommand = "TGFX16",
            ),
        ),
    ),

//    TURBO_GRAFX_CD(
//        core = Core.TGFX16,
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
//    ),

    VECTREX(
        core = Core.Vectrex,
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
    ),
}
