package com.tatsutron.remote.model

import com.tatsutron.remote.util.Constants
import java.io.File

enum class Platform(
    val category: Category,
    val coreId: String? = null,
    val displayName: String? = null,
    val formats: List<Format>,
    val gamesFolder: String? = null,
    val metadata: Boolean,
) {

    ARCADE(
        category = Category.ARCADE,
        formats = listOf(
            Format(
                extension = "mra",
                mbcCommand = "ARCADE",
            ),
        ),
        metadata = false,
    ),

    ARCADIA_2001(
        category = Category.CONSOLE,
        coreId = "Arcadia",
        displayName = "Arcadia 2001",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "ARCADIA",
            ),
        ),
        gamesFolder = "Arcadia",
        metadata = false,
    ),

    ATARI_2600(
        category = Category.CONSOLE,
        coreId = "Atari7800",
        displayName = "Atari 2600",
        formats = listOf(
            Format(
                extension = "a26",
                mbcCommand = "ATARI2600",
            ),
        ),
        gamesFolder = "ATARI7800",
        metadata = true,
    ),

    ATARI_5200(
        category = Category.CONSOLE,
        coreId = "Atari5200",
        displayName = "Atari 5200",
        formats = listOf(
            Format(
                extension = "rom",
                mbcCommand = "ATARI5200",
            ),
        ),
        gamesFolder = "Atari5200",
        metadata = true,
    ),

    ATARI_7800(
        category = Category.CONSOLE,
        coreId = "Atari7800",
        displayName = "Atari 7800",
        formats = listOf(
            Format(
                extension = "a78",
                headerSizeInBytes = 128,
                mbcCommand = "ATARI7800",
            ),
        ),
        gamesFolder = "Atari7800",
        metadata = true,
    ),

    ATARI_LYNX(
        category = Category.HANDHELD,
        coreId = "AtariLynx",
        displayName = "Atari Lynx",
        formats = listOf(
            Format(
                extension = "lnx",
                headerSizeInBytes = 64,
                mbcCommand = "ATARILYNX",
            ),
        ),
        gamesFolder = "AtariLynx",
        metadata = true,
    ),

    BALLY_ASTROCADE(
        category = Category.CONSOLE,
        coreId = "Astrocade",
        displayName = "Bally Astrocade",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "ASTROCADE",
            ),
        ),
        gamesFolder = "Astrocade",
        metadata = false,
    ),

    COLECOVISION(
        category = Category.CONSOLE,
        coreId = "ColecoVision",
        displayName = "ColecoVision",
        formats = listOf(
            Format(
                extension = "col",
                mbcCommand = "COLECO",
            ),
        ),
        gamesFolder = "Coleco",
        metadata = true,
    ),

    FAIRCHILD_CHANNEL_F(
        category = Category.CONSOLE,
        coreId = "ChannelF",
        displayName = "Fairchild Channel F",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "CHANNELF",
            ),
        ),
        gamesFolder = "ChannelF",
        metadata = false,
    ),

    FAMICOM_DISK_SYSTEM(
        category = Category.CONSOLE,
        coreId = "NES",
        displayName = "Famicom Disk System",
        formats = listOf(
            Format(
                extension = "fds",
                headerSizeInBytes = 16,
                mbcCommand = "NES.FDS",
            ),
        ),
        gamesFolder = "NES",
        metadata = true,
    ),

    GAME_BOY(
        category = Category.HANDHELD,
        coreId = "Gameboy",
        displayName = "Game Boy",
        formats = listOf(
            Format(
                extension = "gb",
                mbcCommand = "GAMEBOY",
            ),
        ),
        gamesFolder = "Gameboy",
        metadata = true,
    ),

    GAME_BOY_ADVANCE(
        category = Category.HANDHELD,
        coreId = "GBA",
        displayName = "Game Boy Advance",
        formats = listOf(
            Format(
                extension = "gba",
                mbcCommand = "GBA",
            ),
        ),
        gamesFolder = "GBA",
        metadata = true,
    ),

    GAME_BOY_COLOR(
        category = Category.HANDHELD,
        coreId = "Gameboy",
        displayName = "Game Boy Color",
        formats = listOf(
            Format(
                extension = "gbc",
                mbcCommand = "GAMEBOY.COL",
            ),
        ),
        gamesFolder = "Gameboy",
        metadata = true,
    ),

    GAME_GEAR(
        category = Category.HANDHELD,
        coreId = "SMS",
        displayName = "Game Gear",
        formats = listOf(
            Format(
                extension = "gg",
                mbcCommand = "SMS.GG",
            ),
        ),
        gamesFolder = "SMS",
        metadata = true,
    ),

    INTELLIVISION(
        category = Category.CONSOLE,
        coreId = "Intellivision",
        displayName = "Intellivision",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "INTELLIVISION",
            ),
        ),
        gamesFolder = "Intellivision",
        metadata = true,
    ),

    INTERTON_VC_4000(
        category = Category.CONSOLE,
        coreId = "VC4000",
        displayName = "Interton VC 4000",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "VC4000",
            ),
        ),
        gamesFolder = "VC4000",
        metadata = false,
    ),

    MASTER_SYSTEM(
        category = Category.CONSOLE,
        coreId = "SMS",
        displayName = "Master System",
        formats = listOf(
            Format(
                extension = "sms",
                mbcCommand = "SMS",
            ),
        ),
        gamesFolder = "SMS",
        metadata = true,
    ),

    NEO_GEO(
        category = Category.CONSOLE,
        coreId = "NeoGeo",
        displayName = "Neo Geo",
        formats = listOf(
            Format(
                extension = "neo",
                mbcCommand = "NEOGEO",
            ),
        ),
        gamesFolder = "NEOGEO",
        metadata = false,
    ),

    NINTENDO_ENTERTAINMENT_SYSTEM(
        category = Category.CONSOLE,
        coreId = "NES",
        displayName = "Nintendo Entertainment System",
        formats = listOf(
            Format(
                extension = "nes",
                headerSizeInBytes = 16,
                mbcCommand = "NES",
            ),
        ),
        gamesFolder = "NES",
        metadata = true,
    ),

    ODYSSEY_2(
        category = Category.CONSOLE,
        coreId = "Odyssey2",
        displayName = "Odyssey 2",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "ODYSSEY2",
            ),
        ),
        gamesFolder = "Odyssey2",
        metadata = true,
    ),

    PLAYSTATION(
        category = Category.CONSOLE,
        coreId = "PSX",
        displayName = "PlayStation",
        formats = listOf(
            Format(
                extension = "cue",
                mbcCommand = "PSX",
            ),
        ),
        gamesFolder = "PSX",
        metadata = false,
    ),

    SEGA_32X(
        category = Category.CONSOLE,
        coreId = "S32X",
        displayName = "Sega 32X",
        formats = listOf(
            Format(
                extension = "32x",
                mbcCommand = "S32X",
            ),
        ),
        gamesFolder = "S32X",
        metadata = true,
    ),

    SEGA_CD(
        category = Category.CONSOLE,
        coreId = "MegaCD",
        displayName = "Sega CD",
        formats = listOf(
            Format(
                extension = "chd",
                mbcCommand = "MEGACD",
            ),
            Format(
                extension = "cue",
                mbcCommand = "MEGACD.CUE",
            ),
        ),
        gamesFolder = "MegaCD",
        metadata = false,
    ),

    SEGA_GENESIS(
        category = Category.CONSOLE,
        coreId = "Genesis",
        displayName = "Sega Genesis",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "GENESIS",
            ),
            Format(
                extension = "gen",
                mbcCommand = "GENESIS",
            ),
            Format(
                extension = "md",
                mbcCommand = "GENESIS",
            ),
        ),
        gamesFolder = "Genesis",
        metadata = true,
    ),

    SG_1000(
        category = Category.CONSOLE,
        coreId = "ColecoVision",
        displayName = "SG-1000",
        formats = listOf(
            Format(
                extension = "sg",
                mbcCommand = "COLECO.SG",
            ),
        ),
        gamesFolder = "Coleco",
        metadata = true,
    ),

    SUPER_GRAFX(
        category = Category.CONSOLE,
        coreId = "TurboGrafx16",
        displayName = "SuperGrafx",
        formats = listOf(
            Format(
                extension = "sgx",
                mbcCommand = "SUPERGRAFX",
            ),
        ),
        gamesFolder = "TGFX16",
        metadata = true,
    ),

    SUPER_NINTENDO(
        category = Category.CONSOLE,
        coreId = "SNES",
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
        gamesFolder = "SNES",
        metadata = true,
    ),

    TURBO_GRAFX_16(
        category = Category.CONSOLE,
        coreId = "TurboGrafx16",
        displayName = "TurboGrafx-16",
        formats = listOf(
            Format(
                extension = "pce",
                mbcCommand = "TGFX16",
            ),
        ),
        gamesFolder = "TGFX16",
        metadata = true,
    ),

    TURBO_GRAFX_CD(
        category = Category.CONSOLE,
        coreId = "TurboGrafx16",
        displayName = "TurboGrafx-CD",
        formats = listOf(
            Format(
                extension = "chd",
                mbcCommand = "TGFX16-CD",
            ),
            Format(
                extension = "cue",
                mbcCommand = "TGFX16-CD.CUE",
            ),
        ),
        gamesFolder = "TGFX16-CD",
        metadata = false,
    ),

    VECTREX(
        category = Category.CONSOLE,
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
        gamesFolder = "Vectrex",
        metadata = true,
    ),

    WONDERSWAN(
        category = Category.HANDHELD,
        coreId = "WonderSwan",
        displayName = "WonderSwan",
        formats = listOf(
            Format(
                extension = "ws",
                mbcCommand = "WONDERSWAN",
            ),
        ),
        gamesFolder = "WonderSwan",
        metadata = true,
    ),

    WONDERSWAN_COLOR(
        category = Category.HANDHELD,
        coreId = "WonderSwan",
        displayName = "WonderSwan Color",
        formats = listOf(
            Format(
                extension = "wsc",
                mbcCommand = "WONDERSWAN.COL",
            ),
        ),
        gamesFolder = "WonderSwan",
        metadata = true,
    );

    enum class Category {
        ARCADE,
        CONSOLE,
        COMPUTER,
        HANDHELD,
    }

    val gamesPath: String?
        get() = when {
            this == ARCADE ->
                Constants.ARCADE_PATH
            gamesFolder != null ->
                File(Constants.GAMES_PATH, gamesFolder).path
            else -> null
        }
}
