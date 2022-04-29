package com.tatsutron.remote.util

import com.tatsutron.remote.model.Game
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import java.io.File

interface ScanService {
    @GET("/scan/{extensions}/{path}")
    fun scan(
        @Path("extensions") extensions: String,
        @Path("path") path: String,
    ): Call<List<String>>
}

interface PlayService {
    @PUT("/play")
    fun play(@Body body: Map<String, String>): Call<Unit>
}

object Http {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://${Constants.HOST}:${Constants.PORT}")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val scanService = retrofit.create(ScanService::class.java)
    private val playService = retrofit.create(PlayService::class.java)

    fun scan(extensions: String, path: String) =
        scanService.scan(extensions, path)
            .execute()
            .body()
            ?: listOf()

    fun play(game: Game) {
        val mgl = game.platform.mgl!!
        val path = File(game.path).name
        val body = mapOf(
            Pair("rbf", mgl.rbf),
            Pair("delay", mgl.delay),
            Pair("index", mgl.index),
            Pair("path", path),
            Pair("type", mgl.type),
        )
        playService.play(body)
            .execute()
    }
}
