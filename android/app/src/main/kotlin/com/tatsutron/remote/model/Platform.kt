package com.tatsutron.remote.model

import com.tatsutron.remote.util.Constants
import java.io.File

enum class Platform(
    val coreId: String? = null,
    val displayName: String? = null,
    val formats: List<Format>,
    val gamesFolder: String? = null,
    val metadata: Boolean,
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
                mbcCommand = "ARCADIA",
            ),
        ),
        gamesFolder = "Arcadia",
        metadata = false,
    ),

    ATARI_2600(
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

    val corePath: String?
        get() = if (coreId != null) {
            File(Constants.CONSOLE_PATH, coreId).path
        } else {
            null
        }

    val gamesPath: String?
        get() = if (gamesFolder != null) {
            File(Constants.GAMES_PATH, gamesFolder).path
        } else {
            null
        }
}
