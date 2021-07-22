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
            ?.let {
                it.gamesPath
            }
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

    fun getGameById(id: Long) =
        database?.gamesQueries
            ?.selectById(id)
            ?.executeAsOneOrNull()
            ?.let {
                game(it)
            }

    fun clearGamesByCore(core: String) {
        database?.gamesQueries
            ?.deleteByCore(core)
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
}
