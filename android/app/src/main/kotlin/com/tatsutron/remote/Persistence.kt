package com.tatsutron.remote

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.tatsutron.remote.data.Games
import com.tatsutron.remote.model.Config
import com.tatsutron.remote.model.Game
import java.io.File
import java.io.FileOutputStream
import java.util.*

object Persistence {

    private var database: Database? = null

    fun init(context: Context) {
        // TODO Fix (part of) hardcoded path
        val dir = "/data/data/com.tatsutron.remote/databases"
        val name = "app.db"
        val path = File(dir, name).path
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
            saveConfig(
                Config(
                    host = Constants.HOST,
                    scriptsPath = Constants.SCRIPTS_PATH,
                ),
            )
        }
    }

    private fun saveConfig(config: Config) {
        database?.configQueries
            ?.save(
                host = config.host,
                id = 0,
                scriptsPath = config.scriptsPath,
            )
    }

    fun getConfig() =
        database?.configQueries
            ?.select()
            ?.executeAsOneOrNull()
            ?.let {
                Config(
                    host = it.host,
                    scriptsPath = it.scriptsPath,
                )
            }

    fun saveHost(host: String) {
        getConfig()
            ?.let {
                saveConfig(
                    Config(
                        host = host,
                        scriptsPath = it.scriptsPath,
                    ),
                )
            }
    }

    fun saveScriptsPath(scriptsPath: String) {
        getConfig()
            ?.let {
                saveConfig(
                    Config(
                        host = it.host,
                        scriptsPath = scriptsPath,
                    ),
                )
            }
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

    fun saveGamesPath(core: String, gamesPath: String) {
        database?.coresQueries
            ?.save(gamesPath, core)
    }

    fun getGamesPath(core: String): String =
        database?.coresQueries
            ?.selectByName(core)
            ?.executeAsOneOrNull()
            ?.gamesPath
            ?: File(Constants.GAMES_PATH, core).path

    fun saveGame(core: String, path: String, hash: String?) {
        database?.gamesQueries
            ?.save(core, path, hash)
    }

    fun getGamesByCore(core: String) =
        database?.gamesQueries
            ?.selectByCore(core)
            ?.executeAsList()
            ?.map {
                game(it)
            }
            ?.sortedBy {
                File(it.path).name
            }
            ?: listOf()

    fun getGameByPath(path: String) =
        database?.gamesQueries
            ?.selectByPath(path)
            ?.executeAsOneOrNull()
            ?.let {
                game(it)
            }

    fun getGameBySha1(sha1: String) =
        database?.gamesQueries
            ?.selectBySha1(sha1)
            ?.executeAsOneOrNull()
            ?.let {
                game(it)
            }

    fun deleteGame(path: String) {
        database?.gamesQueries
            ?.deleteByPath(path)
    }

    private fun game(dao: Games) =
        Game(
            core = Core.valueOf(dao.core),
            path = dao.path,
            sha1 = dao.sha1,
            region = dao.sha1?.let {
                database?.regionsQueries
                    ?.selectBySha1(it.toUpperCase(Locale.getDefault()))
                    ?.executeAsOneOrNull()
            },
            release = dao.sha1?.let {
                database?.releasesQueries
                    ?.selectBySha1(it.toUpperCase(Locale.getDefault()))
                    ?.executeAsOneOrNull()
            },
            system = dao.sha1?.let {
                database?.systemsQueries
                    ?.selectBySha1(it.toUpperCase(Locale.getDefault()))
                    ?.executeAsOneOrNull()
            },
        )
}
