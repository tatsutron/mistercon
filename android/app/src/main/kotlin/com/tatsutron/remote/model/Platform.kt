package com.tatsutron.remote.model

// TODO Make this whole thing immutable
//  and find another way to deal with the paths
enum class Platform(
    val coreId: String? = null,
    var corePath: String? = null,
    val displayName: String? = null,
    val formats: List<Format>,
    val gamesFolderDefault: String? = null,
    var gamesPath: String? = null,
    val metadata: Boolean,
    val mgl: Mgl? = null,
) {

    ARCADE(
        formats = listOf(
            Format(
                extension = "mra",
                mbcCommand = "ARCADE",
            ),
        ),
        metadata = false,
    ),

    ARCADIA_2001(
        coreId = "Arcadia",
        displayName = "Arcadia 2001",
        formats = listOf(
            Format(
                extension = "bin",
            ),
        ),
        gamesFolderDefault = "Arcadia",
        metadata = false,
        mgl = Mgl(
            delay = "2",
            index = "0",
            rbf = "_Console/Arcadia",
            type = "f",
        ),
    ),

    ATARI_2600(
        coreId = "Atari7800",
        displayName = "Atari 2600",
        formats = listOf(
            Format(
                extension = "a26",
            ),
        ),
        gamesFolderDefault = "ATARI7800",
        metadata = true,
        mgl = Mgl(
            delay = "2",
            index = "0",
            rbf = "_Console/Atari7800",
            type = "f",
        ),
    ),

    ATARI_5200(
        coreId = "Atari5200",
        displayName = "Atari 5200",
        formats = listOf(
            Format(
                extension = "rom",
                mbcCommand = "ATARI5200",
            ),
        ),
        gamesFolderDefault = "Atari5200",
        metadata = true,
    ),

    ATARI_7800(
        coreId = "Atari7800",
        displayName = "Atari 7800",
        formats = listOf(
            Format(
                extension = "a78",
                headerSizeInBytes = 128,
                mbcCommand = "ATARI7800",
            ),
        ),
        gamesFolderDefault = "Atari7800",
        metadata = true,
    ),

    ATARI_LYNX(
        coreId = "AtariLynx",
        displayName = "Atari Lynx",
        formats = listOf(
            Format(
                extension = "lnx",
                headerSizeInBytes = 64,
                mbcCommand = "ATARILYNX",
            ),
        ),
        gamesFolderDefault = "AtariLynx",
        metadata = true,
    ),

    BALLY_ASTROCADE(
        coreId = "Astrocade",
        displayName = "Bally Astrocade",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "ASTROCADE",
            ),
        ),
        gamesFolderDefault = "Astrocade",
        metadata = false,
    ),

    COLECOVISION(
        coreId = "ColecoVision",
        displayName = "ColecoVision",
        formats = listOf(
            Format(
                extension = "col",
                mbcCommand = "COLECO",
            ),
        ),
        gamesFolderDefault = "Coleco",
        metadata = true,
    ),

    FAIRCHILD_CHANNEL_F(
        coreId = "ChannelF",
        displayName = "Fairchild Channel F",
        formats = listOf(
            Format(
                extension = "bin",
            ),
        ),
        gamesFolderDefault = "ChannelF",
        metadata = false,
        mgl = Mgl(
            delay = "2",
            index = "0",
            rbf = "_Console/ChannelF",
            type = "f",
        ),
    ),

    FAMICOM_DISK_SYSTEM(
        coreId = "NES",
        displayName = "Famicom Disk System",
        formats = listOf(
            Format(
                extension = "fds",
                headerSizeInBytes = 16,
            ),
        ),
        gamesFolderDefault = "NES",
        metadata = true,
        mgl = Mgl(
            delay = "2",
            index = "0",
            rbf = "_Console/NES",
            type = "f",
        ),
    ),

    GAME_BOY(
        coreId = "Gameboy",
        displayName = "Game Boy",
        formats = listOf(
            Format(
                extension = "gb",
            ),
        ),
        gamesFolderDefault = "Gameboy",
        metadata = true,
        mgl = Mgl(
            delay = "2",
            index = "0",
            rbf = "_Console/Gameboy",
            type = "f",
        ),
    ),

    GAME_BOY_ADVANCE(
        coreId = "GBA",
        displayName = "Game Boy Advance",
        formats = listOf(
            Format(
                extension = "gba",
            ),
        ),
        gamesFolderDefault = "GBA",
        metadata = true,
        mgl = Mgl(
            delay = "2",
            index = "0",
            rbf = "_Console/GBA",
            type = "f",
        ),
    ),

    GAME_BOY_COLOR(
        coreId = "Gameboy",
        displayName = "Game Boy Color",
        formats = listOf(
            Format(
                extension = "gbc",
            ),
        ),
        gamesFolderDefault = "Gameboy",
        metadata = true,
        mgl = Mgl(
            delay = "2",
            index = "0",
            rbf = "_Console/Gameboy",
            type = "f",
        ),
    ),

    GAME_GEAR(
        coreId = "SMS",
        displayName = "Game Gear",
        formats = listOf(
            Format(
                extension = "gg",
            ),
        ),
        gamesFolderDefault = "SMS",
        metadata = true,
        mgl = Mgl(
            delay = "1",
            index = "2",
            rbf = "_Console/SMS",
            type = "f",
        ),
    ),

    INTELLIVISION(
        coreId = "Intellivision",
        displayName = "Intellivision",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "INTELLIVISION",
            ),
        ),
        gamesFolderDefault = "Intellivision",
        metadata = true,
    ),

    INTERTON_VC_4000(
        coreId = "VC4000",
        displayName = "Interton VC 4000",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "VC4000",
            ),
        ),
        gamesFolderDefault = "VC4000",
        metadata = false,
    ),

    MASTER_SYSTEM(
        coreId = "SMS",
        displayName = "Master System",
        formats = listOf(
            Format(
                extension = "sms",
            ),
        ),
        gamesFolderDefault = "SMS",
        metadata = true,
        mgl = Mgl(
            delay = "1",
            index = "1",
            rbf = "_Console/SMS",
            type = "f",
        ),
    ),

    NEO_GEO(
        coreId = "NeoGeo",
        displayName = "Neo Geo",
        formats = listOf(
            Format(
                extension = "neo",
            ),
        ),
        gamesFolderDefault = "NEOGEO",
        metadata = false,
        mgl = Mgl(
            delay = "1",
            index = "1",
            rbf = "_Console/NeoGeo",
            type = "f",
        ),
    ),

    NINTENDO_ENTERTAINMENT_SYSTEM(
        coreId = "NES",
        displayName = "Nintendo Entertainment System",
        formats = listOf(
            Format(
                extension = "nes",
                headerSizeInBytes = 16,
            ),
        ),
        gamesFolderDefault = "NES",
        metadata = true,
        mgl = Mgl(
            delay = "2",
            index = "0",
            rbf = "_Console/NES",
            type = "f",
        ),
    ),

    ODYSSEY_2(
        coreId = "Odyssey2",
        displayName = "Odyssey 2",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "ODYSSEY2",
            ),
        ),
        gamesFolderDefault = "Odyssey2",
        metadata = true,
    ),

    PLAYSTATION(
        coreId = "PSX",
        displayName = "PlayStation",
        formats = listOf(
            Format(
                extension = "chd",
            ),
        ),
        gamesFolderDefault = "PSX",
        metadata = false,
        mgl = Mgl(
            delay = "1",
            index = "1",
            rbf = "_Console/PSX",
            type = "s",
        ),
    ),

    SEGA_32X(
        coreId = "S32X",
        displayName = "Sega 32X",
        formats = listOf(
            Format(
                extension = "32x",
            ),
        ),
        gamesFolderDefault = "S32X",
        metadata = true,
        mgl = Mgl(
            delay = "1",
            index = "0",
            rbf = "_Console/S32X",
            type = "f",
        ),
    ),

    SEGA_CD(
        coreId = "MegaCD",
        displayName = "Sega CD",
        formats = listOf(
            Format(
                extension = "chd",
            ),
        ),
        gamesFolderDefault = "MegaCD",
        metadata = false,
        mgl = Mgl(
            delay = "1",
            index = "0",
            rbf = "_Console/MegaCD",
            type = "s",
        ),
    ),

    SEGA_GENESIS(
        coreId = "Genesis",
        displayName = "Sega Genesis",
        formats = listOf(
            Format(
                extension = "bin",
            ),
            Format(
                extension = "gen",
            ),
            Format(
                extension = "md",
            ),
        ),
        gamesFolderDefault = "Genesis",
        metadata = true,
        mgl = Mgl(
            delay = "1",
            index = "0",
            rbf = "_Console/Genesis",
            type = "f",
        ),
    ),

    SG_1000(
        coreId = "ColecoVision",
        displayName = "SG-1000",
        formats = listOf(
            Format(
                extension = "sg",
                mbcCommand = "COLECO.SG",
            ),
        ),
        gamesFolderDefault = "Coleco",
        metadata = true,
    ),

    SUPER_GRAFX(
        coreId = "TurboGrafx16",
        displayName = "SuperGrafx",
        formats = listOf(
            Format(
                extension = "sgx",
                mbcCommand = "SUPERGRAFX",
            ),
        ),
        gamesFolderDefault = "TGFX16",
        metadata = true,
    ),

    SUPER_NINTENDO(
        coreId = "SNES",
        displayName = "Super Nintendo",
        formats = listOf(
            Format(
                extension = "sfc",
            ),
            Format(
                extension = "smc",
            ),
        ),
        gamesFolderDefault = "SNES",
        metadata = true,
        mgl = Mgl(
            delay = "2",
            index = "0",
            rbf = "_Console/SNES",
            type = "f",
        ),
    ),

    TURBO_GRAFX_16(
        coreId = "TurboGrafx16",
        displayName = "TurboGrafx-16",
        formats = listOf(
            Format(
                extension = "pce",
            ),
        ),
        gamesFolderDefault = "TGFX16",
        metadata = true,
        mgl = Mgl(
            delay = "1",
            index = "0",
            rbf = "_Console/TurboGrafx16",
            type = "f",
        ),
    ),

    TURBO_GRAFX_CD(
        coreId = "TurboGrafx16",
        displayName = "TurboGrafx-CD",
        formats = listOf(
            Format(
                extension = "chd",
            ),
        ),
        gamesFolderDefault = "TGFX16-CD",
        metadata = false,
        mgl = Mgl(
            delay = "1",
            index = "0",
            prefix = "../TGFX16-CD/",
            rbf = "_Console/TurboGrafx16",
            type = "s",
        ),
    ),

    VECTREX(
        coreId = "Vectrex",
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
        metadata = true,
    ),

    WONDERSWAN(
        coreId = "WonderSwan",
        displayName = "WonderSwan",
        formats = listOf(
            Format(
                extension = "ws",
                mbcCommand = "WONDERSWAN",
            ),
        ),
        gamesFolderDefault = "WonderSwan",
        metadata = true,
    ),

    WONDERSWAN_COLOR(
        coreId = "WonderSwan",
        displayName = "WonderSwan Color",
        formats = listOf(
            Format(
                extension = "wsc",
                mbcCommand = "WONDERSWAN.COL",
            ),
        ),
        gamesFolderDefault = "WonderSwan",
        metadata = true,
    );
}
