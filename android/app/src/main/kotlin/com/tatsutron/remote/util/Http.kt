package com.tatsutron.remote.util

import com.tatsutron.remote.model.Game
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

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

    private var retrofit: Retrofit? = null
    private var scanService: ScanService? = null
    private var playService: PlayService? = null

    init {
        reset()
    }

    fun reset() {
        val host = Persistence.getConfig()?.host
        val port = Constants.PORT
        retrofit = Retrofit.Builder()
            .baseUrl("http://${host}:${port}")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        scanService = retrofit?.create(ScanService::class.java)
        playService = retrofit?.create(PlayService::class.java)
    }

    fun scan(extensions: String, path: String) =
        scanService
            ?.scan(extensions, path)
            ?.execute()
            ?.body()
            ?: listOf()

    fun play(game: Game) {
        val mgl = game.platform.mgl!!
        val path = "${mgl.prefix}${game.path}"
            .replace("${game.platform.gamesPath}/", "")
        val body = mapOf(
            Pair("rbf", mgl.rbf),
            Pair("delay", mgl.delay),
            Pair("index", mgl.index),
            Pair("path", path),
            Pair("type", mgl.type),
        )
        playService
            ?.play(body)
            ?.execute()
    }
}
