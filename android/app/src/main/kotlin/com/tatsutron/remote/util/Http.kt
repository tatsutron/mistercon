package com.tatsutron.remote.util

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ScanService {
    @GET("/scan/{extensions}/{path}")
    fun getScan(
        @Path("extensions") extensions: String,
        @Path("path") path: String,
    ): Call<List<String>>
}

object Http {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://${Constants.HOST}:${Constants.PORT}")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val scanService = retrofit.create(ScanService::class.java)

    fun scan(extensions: String, path: String) =
        scanService.getScan(extensions, path)
            .execute()
            .body()
            ?: listOf()
}
