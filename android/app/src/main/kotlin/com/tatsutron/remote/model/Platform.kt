package com.tatsutron.remote.model

import com.tatsutron.remote.util.Constants
import java.io.File

enum class Platform(
    val category: Category,
    val displayName: String? = null,
    val formats: List<Format>,
    val gamesFolder: String? = null,
    val headerSizeInBytes: Int? = null,
    val media: Media,
    val metadata: Boolean,
    val supportsZip: Boolean,
) {

    ARCADE(
        category = Category.ARCADE,
        displayName = "Arcades",
        formats = listOf(
            Format(
                extension = "mra",
                mbcCommand = "ARCADE",
            ),
        ),
        media = Media.PRINTED_CIRCUIT_BOARD,
        metadata = false,
        supportsZip = false,
    ),

    ARCADIA_2001(
        category = Category.CONSOLE,
        displayName = "Arcadia 2001",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "ARCADIA",
            ),
        ),
        gamesFolder = "Arcadia",
        media = Media.ROM_CARTRIDGE,
        metadata = false,
        supportsZip = false,
    ),

    ATARI_2600(
        category = Category.CONSOLE,
        displayName = "Atari 2600",
        formats = listOf(
            Format(
                extension = "a26",
                mbcCommand = "ATARI2600",
            ),
        ),
        gamesFolder = "ATARI7800",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    ATARI_5200(
        category = Category.CONSOLE,
        displayName = "Atari 5200",
        formats = listOf(
            Format(
                extension = "rom",
                mbcCommand = "ATARI5200",
            ),
        ),
        gamesFolder = "Atari5200",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    ATARI_7800(
        category = Category.CONSOLE,
        displayName = "Atari 7800",
        formats = listOf(
            Format(
                extension = "a78",
                mbcCommand = "ATARI7800",
            ),
        ),
        gamesFolder = "Atari7800",
        headerSizeInBytes = 128,
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    ATARI_LYNX(
        category = Category.HANDHELD,
        displayName = "Atari Lynx",
        formats = listOf(
            Format(
                extension = "lnx",
                mbcCommand = "ATARILYNX",
            ),
        ),
        gamesFolder = "AtariLynx",
        headerSizeInBytes = 64,
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    BALLY_ASTROCADE(
        category = Category.CONSOLE,
        displayName = "Bally Astrocade",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "ASTROCADE",
            ),
        ),
        gamesFolder = "Astrocade",
        media = Media.ROM_CARTRIDGE,
        metadata = false,
        supportsZip = false,
    ),

    COLECOVISION(
        category = Category.CONSOLE,
        displayName = "ColecoVision",
        formats = listOf(
            Format(
                extension = "col",
                mbcCommand = "COLECO",
            ),
        ),
        gamesFolder = "Coleco",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    FAIRCHILD_CHANNEL_F(
        category = Category.CONSOLE,
        displayName = "Fairchild Channel F",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "CHANNELF",
            ),
        ),
        gamesFolder = "ChannelF",
        media = Media.ROM_CARTRIDGE,
        metadata = false,
        supportsZip = false,
    ),

    FAMICOM_DISK_SYSTEM(
        category = Category.CONSOLE,
        displayName = "Famicom Disk System",
        formats = listOf(
            Format(
                extension = "fds",
                mbcCommand = "NES.FDS",
            ),
        ),
        gamesFolder = "NES",
        headerSizeInBytes = 16,
        media = Media.FLOPPY_DISK,
        metadata = true,
        supportsZip = false,
    ),

    GAME_BOY(
        category = Category.HANDHELD,
        displayName = "Game Boy",
        formats = listOf(
            Format(
                extension = "gb",
                mbcCommand = "GAMEBOY",
            ),
        ),
        gamesFolder = "Gameboy",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    GAME_BOY_ADVANCE(
        category = Category.HANDHELD,
        displayName = "Game Boy Advance",
        formats = listOf(
            Format(
                extension = "gba",
                mbcCommand = "GBA",
            ),
        ),
        gamesFolder = "GBA",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    GAME_BOY_COLOR(
        category = Category.HANDHELD,
        displayName = "Game Boy Color",
        formats = listOf(
            Format(
                extension = "gbc",
                mbcCommand = "GAMEBOY.COL",
            ),
        ),
        gamesFolder = "Gameboy",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    GAME_GEAR(
        category = Category.HANDHELD,
        displayName = "Game Gear",
        formats = listOf(
            Format(
                extension = "gg",
                mbcCommand = "SMS.GG",
            ),
        ),
        gamesFolder = "SMS",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    INTELLIVISION(
        category = Category.CONSOLE,
        displayName = "Intellivision",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "INTELLIVISION",
            ),
        ),
        gamesFolder = "Intellivision",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    INTERTON_VC_4000(
        category = Category.CONSOLE,
        displayName = "Interton VC 4000",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "VC4000",
            ),
        ),
        gamesFolder = "VC4000",
        media = Media.ROM_CARTRIDGE,
        metadata = false,
        supportsZip = false,
    ),

    MASTER_SYSTEM(
        category = Category.CONSOLE,
        displayName = "Master System",
        formats = listOf(
            Format(
                extension = "sms",
                mbcCommand = "SMS",
            ),
        ),
        gamesFolder = "SMS",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    NEO_GEO(
        category = Category.CONSOLE,
        displayName = "Neo Geo",
        formats = listOf(
            Format(
                extension = "neo",
                mbcCommand = "NEOGEO",
            ),
        ),
        gamesFolder = "NEOGEO",
        media = Media.ROM_CARTRIDGE,
        metadata = false,
        supportsZip = false,
    ),

    NINTENDO_ENTERTAINMENT_SYSTEM(
        category = Category.CONSOLE,
        displayName = "Nintendo Entertainment System",
        formats = listOf(
            Format(
                extension = "nes",
                mbcCommand = "NES",
            ),
        ),
        gamesFolder = "NES",
        headerSizeInBytes = 16,
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = true,
    ),

    ODYSSEY_2(
        category = Category.CONSOLE,
        displayName = "Odyssey 2",
        formats = listOf(
            Format(
                extension = "bin",
                mbcCommand = "ODYSSEY2",
            ),
        ),
        gamesFolder = "Odyssey2",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    PLAYSTATION(
        category = Category.CONSOLE,
        displayName = "PlayStation",
        formats = listOf(
            Format(
                extension = "cue",
                mbcCommand = "PSX",
            ),
        ),
        gamesFolder = "PSX",
        media = Media.OPTICAL_DISC,
        metadata = false,
        supportsZip = false,
    ),

    SEGA_32X(
        category = Category.CONSOLE,
        displayName = "Sega 32X",
        formats = listOf(
            Format(
                extension = "32x",
                mbcCommand = "S32X",
            ),
        ),
        gamesFolder = "S32X",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    SEGA_CD(
        category = Category.CONSOLE,
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
        media = Media.OPTICAL_DISC,
        metadata = false,
        supportsZip = false,
    ),

    SEGA_GENESIS(
        category = Category.CONSOLE,
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
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    SG_1000(
        category = Category.CONSOLE,
        displayName = "SG-1000",
        formats = listOf(
            Format(
                extension = "sg",
                mbcCommand = "COLECO.SG",
            ),
        ),
        gamesFolder = "Coleco",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    SUPER_GRAFX(
        category = Category.CONSOLE,
        displayName = "SuperGrafx",
        formats = listOf(
            Format(
                extension = "sgx",
                mbcCommand = "SUPERGRAFX",
            ),
        ),
        gamesFolder = "TGFX16",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    SUPER_NINTENDO(
        category = Category.CONSOLE,
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
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = true,
    ),

    TURBO_GRAFX_16(
        category = Category.CONSOLE,
        displayName = "TurboGrafx-16",
        formats = listOf(
            Format(
                extension = "pce",
                mbcCommand = "TGFX16",
            ),
        ),
        gamesFolder = "TGFX16",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    TURBO_GRAFX_CD(
        category = Category.CONSOLE,
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
        media = Media.OPTICAL_DISC,
        metadata = false,
        supportsZip = false,
    ),

    VECTREX(
        category = Category.CONSOLE,
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
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    WONDERSWAN(
        category = Category.HANDHELD,
        displayName = "WonderSwan",
        formats = listOf(
            Format(
                extension = "ws",
                mbcCommand = "WONDERSWAN",
            ),
        ),
        gamesFolder = "WonderSwan",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    ),

    WONDERSWAN_COLOR(
        category = Category.HANDHELD,
        displayName = "WonderSwan Color",
        formats = listOf(
            Format(
                extension = "wsc",
                mbcCommand = "WONDERSWAN.COL",
            ),
        ),
        gamesFolder = "WonderSwan",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        supportsZip = false,
    );

    enum class Category(val path: String) {
        ARCADE(Constants.ARCADE_PATH),
        CONSOLE(Constants.CONSOLE_PATH),
        COMPUTER(Constants.COMPUTER_PATH),
        HANDHELD(Constants.CONSOLE_PATH),
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
