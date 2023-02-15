package com.tatsutron.remote.util

import android.annotation.SuppressLint
import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.tatsutron.remote.Database
import com.tatsutron.remote.data.Games
import com.tatsutron.remote.data.SelectBySha1
import com.tatsutron.remote.model.*
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
    }

    fun saveConfig(config: Config) {
        database?.configQueries
            ?.save(
                host = config.host,
                id = 0,
            )
    }

    fun getConfig() =
        database?.configQueries
            ?.select()
            ?.executeAsOneOrNull()
            ?.let {
                Config(
                    host = it.host,
                )
            }

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
