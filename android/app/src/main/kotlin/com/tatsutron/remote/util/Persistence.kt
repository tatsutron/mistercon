package com.tatsutron.remote.util

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.tatsutron.remote.Database
import com.tatsutron.remote.data.Games
import com.tatsutron.remote.data.SelectBySha1
import com.tatsutron.remote.model.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

object Persistence {

    private lateinit var configFile: File
    private lateinit var config: Config
    private val gson = Gson()
    private var database: Database? = null

    @SuppressLint("SdCardPath")
    fun init(context: Context) {
        configFile = File(context.filesDir, "config.json")
        if (configFile.exists()) {
            config = gson.fromJson(configFile.readText(), Config::class.java)
        } else {
            config = Config()
            configFile.writeText(gson.toJson(config))
        }

        val dir = "/data/data/${context.packageName}/databases"
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
    }

    var host: String
        set(ipAddress) {
            config.host = ipAddress
            configFile.writeText(gson.toJson(config))
        }
        get() = config.host

    fun saveGame(path: String, platform: Platform, sha1: String?) {
        database?.gamesQueries
            ?.save(path, platform.name, sha1)
    }

    fun getGamesByPlatform(platform: Platform) =
        database?.gamesQueries
            ?.selectByPlatform(platform.name)
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

    fun getMetadataBySha1(sha1: String) =
        database?.metadataQueries
            ?.selectBySha1(sha1.toUpperCase(Locale.getDefault()))
            ?.executeAsOneOrNull()
            ?.let {
                metadata(it)
            }

    private fun game(dao: Games) =
        Game(
            platform = Platform.valueOf(dao.platform),
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
