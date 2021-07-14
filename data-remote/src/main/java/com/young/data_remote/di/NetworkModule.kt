package com.young.data_remote.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.young.data_remote.api.SubwayFacilitiesService

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val baseUrl = "https://api.odcloud.kr/api/"

    val NETWORK_TIME_OUT: Long = 5

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {

            level = HttpLoggingInterceptor.Level.BODY

        }

    @Provides
    @Singleton
    fun provideCreateOkHttpClient(
        interceptor: Interceptor,
        bodyInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(bodyInterceptor)
        addInterceptor(interceptor)
        connectTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
        readTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
    }.build()

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor = Interceptor { chain ->

        val builder = chain.request().newBuilder().apply {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
        }

        chain.proceed(builder.build())
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Singleton
    @Provides
    fun provideSubwayFacilitiesService(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): SubwayFacilitiesService =
        retrofit.create(SubwayFacilitiesService::class.java)
}