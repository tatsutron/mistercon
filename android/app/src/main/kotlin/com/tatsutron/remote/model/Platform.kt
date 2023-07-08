package com.tatsutron.remote.model

import android.graphics.Color
import com.tatsutron.remote.R
import com.tatsutron.remote.util.Constants
import java.io.File

enum class Platform(
    val category: Category,
    val displayName: String? = null,
    val gamesFolder: String? = null,
    val headerSizeInBytes: Int? = null,
    val media: Media,
    val metadata: Boolean,
    val mrextId: String,
) {

    ARCADE(
        category = Category.ARCADE,
        displayName = "Arcades",
        media = Media.PRINTED_CIRCUIT_BOARD,
        metadata = false,
        mrextId = "arcade",
    ),

    ARCADIA_2001(
        category = Category.CONSOLE,
        displayName = "Arcadia 2001",
        gamesFolder = "Arcadia",
        media = Media.ROM_CARTRIDGE,
        metadata = false,
        mrextId = "arcadia",
    ),

    ATARI_2600(
        category = Category.CONSOLE,
        displayName = "Atari 2600",
        gamesFolder = "Atari2600",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "atari2600",
    ),

    ATARI_5200(
        category = Category.CONSOLE,
        displayName = "Atari 5200",
        gamesFolder = "ATARI5200",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "atari5200",
    ),

    ATARI_7800(
        category = Category.CONSOLE,
        displayName = "Atari 7800",
        gamesFolder = "ATARI7800",
        headerSizeInBytes = 128,
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "atari7800",
    ),

    ATARI_LYNX(
        category = Category.HANDHELD,
        displayName = "Atari Lynx",
        gamesFolder = "AtariLynx",
        headerSizeInBytes = 64,
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "atarilynx",
    ),

    BALLY_ASTROCADE(
        category = Category.CONSOLE,
        displayName = "Bally Astrocade",
        gamesFolder = "Astrocade",
        media = Media.ROM_CARTRIDGE,
        metadata = false,
        mrextId = "astrocade",
    ),

    COLECOVISION(
        category = Category.CONSOLE,
        displayName = "ColecoVision",
        gamesFolder = "Coleco",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "colecovision",
    ),

    FAIRCHILD_CHANNEL_F(
        category = Category.CONSOLE,
        displayName = "Fairchild Channel F",
        gamesFolder = "ChannelF",
        media = Media.ROM_CARTRIDGE,
        metadata = false,
        mrextId = "channelf",
    ),

    FAMICOM_DISK_SYSTEM(
        category = Category.CONSOLE,
        displayName = "Famicom Disk System",
        gamesFolder = "NES",
        headerSizeInBytes = 16,
        media = Media.FLOPPY_DISK,
        metadata = true,
        mrextId = "fds",
    ),

    GAME_BOY(
        category = Category.HANDHELD,
        displayName = "Game Boy",
        gamesFolder = "GAMEBOY",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "gameboy",
    ),

    GAME_BOY_ADVANCE(
        category = Category.HANDHELD,
        displayName = "Game Boy Advance",
        gamesFolder = "GBA",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "gba",
    ),

    GAME_BOY_COLOR(
        category = Category.HANDHELD,
        displayName = "Game Boy Color",
        gamesFolder = "GBC",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "gameboycolor",
    ),

    GAME_GEAR(
        category = Category.HANDHELD,
        displayName = "Game Gear",
        gamesFolder = "GameGear",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "gamegear",
    ),

    INTELLIVISION(
        category = Category.CONSOLE,
        displayName = "Intellivision",
        gamesFolder = "Intellivision",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "intellivision",
    ),

    INTERTON_VC_4000(
        category = Category.CONSOLE,
        displayName = "Interton VC 4000",
        gamesFolder = "VC4000",
        media = Media.ROM_CARTRIDGE,
        metadata = false,
        mrextId = "vc4000",
    ),

    MASTER_SYSTEM(
        category = Category.CONSOLE,
        displayName = "Master System",
        gamesFolder = "SMS",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "mastersystem",
    ),

    NEO_GEO(
        category = Category.CONSOLE,
        displayName = "Neo Geo",
        gamesFolder = "NEOGEO",
        media = Media.ROM_CARTRIDGE,
        metadata = false,
        mrextId = "neogeo",
    ),

    NINTENDO_ENTERTAINMENT_SYSTEM(
        category = Category.CONSOLE,
        displayName = "Nintendo Entertainment System",
        gamesFolder = "NES",
        headerSizeInBytes = 16,
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "nes",
    ),

    ODYSSEY_2(
        category = Category.CONSOLE,
        displayName = "Odyssey 2",
        gamesFolder = "ODYSSEY2",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "odyssey2",
    ),

    PLAYSTATION(
        category = Category.CONSOLE,
        displayName = "PlayStation",
        gamesFolder = "PSX",
        media = Media.OPTICAL_DISC,
        metadata = false,
        mrextId = "psx",
    ),

    SEGA_32X(
        category = Category.CONSOLE,
        displayName = "Sega 32X",
        gamesFolder = "S32X",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "sega32x",
    ),

    SEGA_CD(
        category = Category.CONSOLE,
        displayName = "Sega CD",
        gamesFolder = "MegaCD",
        media = Media.OPTICAL_DISC,
        metadata = false,
        mrextId = "megacd",
    ),

    SEGA_GENESIS(
        category = Category.CONSOLE,
        displayName = "Sega Genesis",
        gamesFolder = "Genesis",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "genesis",
    ),

    SG_1000(
        category = Category.CONSOLE,
        displayName = "SG-1000",
        gamesFolder = "Coleco",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "sg1000",
    ),

    SUPER_GRAFX(
        category = Category.CONSOLE,
        displayName = "SuperGrafx",
        gamesFolder = "TGFX16",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "supergrafx",
    ),

    SUPER_NINTENDO(
        category = Category.CONSOLE,
        displayName = "Super Nintendo",
        gamesFolder = "SNES",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "snes",
    ),

    TURBO_GRAFX_16(
        category = Category.CONSOLE,
        displayName = "TurboGrafx-16",
        gamesFolder = "TGFX16",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "turbografx16",
    ),

    TURBO_GRAFX_CD(
        category = Category.CONSOLE,
        displayName = "TurboGrafx-CD",
        gamesFolder = "TGFX16-CD",
        media = Media.OPTICAL_DISC,
        metadata = false,
        mrextId = "turbografx16cd",
    ),

    VECTREX(
        category = Category.CONSOLE,
        displayName = "Vectrex",
        gamesFolder = "VECTREX",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "vectrex",
    ),

    WONDERSWAN(
        category = Category.HANDHELD,
        displayName = "WonderSwan",
        gamesFolder = "WonderSwan",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "wonderswan",
    ),

    WONDERSWAN_COLOR(
        category = Category.HANDHELD,
        displayName = "WonderSwan Color",
        gamesFolder = "WonderSwanColor",
        media = Media.ROM_CARTRIDGE,
        metadata = true,
        mrextId = "wonderswancolor",
    );

    enum class Category(
        val displayName: String,
        val path: String,
    ) {
        ARCADE(
            displayName = "Arcades",
            path = Constants.ARCADE_PATH
        ),
        CONSOLE(
            displayName = "Consoles",
            path = Constants.CONSOLE_PATH
        ),
        COMPUTER(
            displayName = "Computers",
            path = Constants.COMPUTER_PATH,
        ),
        HANDHELD(
            displayName = "Handhelds",
            path = Constants.CONSOLE_PATH,
        ),
    }

    val gamesPath: String?
        get() = when {
            this == ARCADE ->
                Constants.ARCADE_PATH
            gamesFolder != null ->
                File(Constants.GAMES_PATH, gamesFolder).path
            else -> null
        }

    private val subscriptColors = listOf(
        R.color.analogous_200,
        R.color.complementary_200,
        R.color.triadic_200,
    )

    val subscriptColor: Int
        get() = subscriptColors[ordinal % subscriptColors.size]
}
