package com.tatsutron.remote.util

object Constants {
    private const val VOLUME = "/media/fat"
    const val MISTERCON_ROOT = "${VOLUME}/tatsutron/mistercon"

    const val ARCADE_PATH = "${VOLUME}/_Arcade"
    const val CONSOLE_PATH = "${VOLUME}/_Console"
    const val GAMES_PATH = "${VOLUME}/games"
    const val HASH_PATH = "${MISTERCON_ROOT}/hash"
    const val HOST = "MiSTer"
    const val LIST_PATH = "${MISTERCON_ROOT}/list"
    const val MBC_PATH = "${MISTERCON_ROOT}/mbc"
    const val PORT = 8080
    const val SCRIPTS_PATH = "${VOLUME}/Scripts"
}
