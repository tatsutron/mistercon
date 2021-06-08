package com.tatsutron.remote

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.tatsutron.remote.data.Games
import java.io.File
import java.io.FileOutputStream
import java.util.*

object Persistence {

    private var database: Database? = null

    fun init(context: Context) {
        val dir = "/data/data/com.tatsutron.remote/databases"
        val name = "app.db"
        val path = "$dir/$name"
        if (!File(path).exists()) {
            File(dir).mkdir()
            File(path).createNewFile()
            context.assets.open("openvgdb.sqlite")
                .copyTo(FileOutputStream(path))
        }
        database = Database(
            AndroidSqliteDriver(Database.Schema, context, name)
        )
        val config = database?.configQueries
            ?.select()
            ?.executeAsOneOrNull()
        if (config == null) {
            saveConfig(Config())
        }
    }

    private fun saveConfig(config: Config) {
        database?.configQueries
            ?.save(
                gamesPath = config.gamesPath,
                host = config.host,
                id = 0,
                mbcPath = config.mbcPath,
                scriptsPath = config.scriptsPath,
            )
    }

    private fun getConfig() =
        database?.configQueries
            ?.select()
            ?.executeAsOneOrNull()
            ?.let {
                Config(
                    gamesPath = it.gamesPath,
                    host = it.host,
                    mbcPath = it.mbcPath,
                    scriptsPath = it.scriptsPath,
                )
            }

    fun saveHost(host: String) {
        getConfig()
            ?.let {
                it.host = host
                saveConfig(it)
            }
    }

    fun getHost() = getConfig()?.host

    fun saveGamesPath(path: String) {
        getConfig()
            ?.let {
                it.gamesPath = path
                saveConfig(it)
            }
    }

    fun getGamesPath() = getConfig()?.gamesPath

    fun getMbcPath() = getConfig()?.mbcPath

    fun getScriptsPath() = getConfig()?.scriptsPath

    fun saveGame(core: String, path: String, hash: String?) {
        database?.gamesQueries
            ?.save(core, path, hash)
    }

    fun getGameList() =
        database?.gamesQueries
            ?.selectAll()
            ?.executeAsList()
            ?.map {
                game(it)
            }
            ?: listOf()

    fun getGame(id: Long) =
        database?.gamesQueries
            ?.selectById(id)
            ?.executeAsOneOrNull()
            ?.let {
                game(it)
            }

    fun clearGames() {
        database?.gamesQueries
            ?.clear()
    }

    private fun game(dao: Games): Game {
        val sha1 = dao.sha1?.toUpperCase(Locale.getDefault())
        return Game(
            core = Core.valueOf(dao.core),
            id = dao.id,
            path = dao.path,
            region = database?.regionsQueries
                ?.selectBySha1(sha1)
                ?.executeAsOneOrNull(),
            release = database?.releasesQueries
                ?.selectBySha1(sha1)
                ?.executeAsOneOrNull(),
            system = database?.systemsQueries
                ?.selectBySha1(sha1)
                ?.executeAsOneOrNull(),
        )
    }

    fun saveScript(filename: String) {
        database?.scriptsQueries
            ?.save(filename)
    }

    fun getScriptList() =
        database?.scriptsQueries
            ?.selectAll()
            ?.executeAsList()
            ?: listOf()

    fun clearScripts() {
        database?.scriptsQueries
            ?.clear()
    }
}
