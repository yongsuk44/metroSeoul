package com.young.data_remote.generate

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nhaarman.mockito_kotlin.mock
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {
    fun RetrofitGenerate(mockWebServer: MockWebServer) : Retrofit =
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClientGenerate())
            .addConverterFactory(GsonConverterFactory.create(GsonGenerate()))
            .build()


    fun OkHttpClientGenerate() : OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(10 , TimeUnit.SECONDS)
            .connectTimeout(10 , TimeUnit.SECONDS)
            .writeTimeout(10 , TimeUnit.SECONDS)
            .build()

    fun GsonGenerate(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

}