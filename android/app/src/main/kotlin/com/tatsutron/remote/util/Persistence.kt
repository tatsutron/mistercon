package com.tatsutron.remote.util

import android.annotation.SuppressLint
import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.tatsutron.remote.Database
import com.tatsutron.remote.data.Games
import com.tatsutron.remote.data.SelectBySha1
import com.tatsutron.remote.model.Config
import com.tatsutron.remote.model.Console
import com.tatsutron.remote.model.Game
import com.tatsutron.remote.model.Metadata
import java.io.File
import java.io.FileOutputStream
import java.util.*

object Persistence {

    private var database: Database? = null

    @SuppressLint("SdCardPath")
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

    fun saveGamesPath(console: Console, gamesPath: String) {
        database?.consolesQueries
            ?.save(gamesPath, console.name)
    }

    fun getGamesPath(console: Console): String =
        database?.consolesQueries
            ?.selectByName(console.name)
            ?.executeAsOneOrNull()
            ?.gamesPath
            ?: File(Constants.GAMES_PATH, console.gamesFolderDefault!!).path

    // TODO Rename `core` to `platform`
    fun saveGame(core: String, path: String, hash: String?) {
        database?.gamesQueries
            ?.save(core, path, hash)
    }

    // TODO Rename to `getGamesByPlatform`
    fun getGamesByConsole(console: Console) =
        database?.gamesQueries
            ?.selectByConsole(console.name)
            ?.executeAsList()
            ?.map {
                game(it)
            }
            ?.sortedBy {
                File(it.path).name.toLowerCase(Locale.getDefault())
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

    fun favoriteGame(game: Game, favorite: Boolean) {
        database?.gamesQueries
            ?.favoriteByPath(if (favorite) 1L else 0L, game.path)
    }

    fun getGamesByFavorite() =
        database?.gamesQueries
            ?.selectByFavorite()
            ?.executeAsList()
            ?.map {
                game(it)
            }
            ?.sortedBy {
                File(it.path).name.toLowerCase(Locale.getDefault())
            }
            ?: listOf()

    fun deleteGame(path: String) {
        database?.gamesQueries
            ?.deleteByPath(path)
    }

    // TODO Rename to `getMetadataByIdentifier`
    fun getMetadataBySha1(sha1: String) =
        database?.metadataQueries
            ?.selectBySha1(sha1.toUpperCase(Locale.getDefault()))
            ?.executeAsOneOrNull()
            ?.let {
                metadata(it)
            }

    private fun game(dao: Games) =
        Game(
            console = Console.valueOf(dao.console),
            favorite = dao.favorite != 0L,
            path = dao.path,
            sha1 = dao.sha1,
        )

    private fun metadata(dao: SelectBySha1) =
        Metadata(
            backCover = dao.backCover,
            cartridge = dao.cartridge,
            description = dao.description,
            developer = dao.developer,
            frontCover = dao.frontCover,
            genre = dao.genre,
            publisher = dao.publisher,
            releaseDate = dao.releaseDate,
            region = dao.region,
        )
}
