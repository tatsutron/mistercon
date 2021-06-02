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
    }

    fun saveGame(core: String, filename: String, hash: String?) {
        database?.gamesQueries?.save(core, filename, hash)
    }

    fun getGameList() =
        database?.gamesQueries
            ?.selectAll()
            ?.executeAsList()
            ?.map {
                game(it)
            }
            ?: listOf()

    fun getGame(filename: String) =
        database?.gamesQueries
            ?.selectByFilename(filename)
            ?.executeAsOneOrNull()
            ?.let {
                game(it)
            }

    fun clearGames() {
        database?.gamesQueries?.clear()
    }


    private fun game(dao: Games): Game {
        val sha1 = dao.sha1?.toUpperCase(Locale.getDefault())
        return Game(
            core = Core.valueOf(dao.core),
            filename = dao.filename,
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
