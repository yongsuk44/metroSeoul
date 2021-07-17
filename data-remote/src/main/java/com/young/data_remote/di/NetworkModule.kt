package com.young.data_remote.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.young.data_remote.api.SubWayTelService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.young.data_remote.api.SubwayFacilitiesService
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PortalUrlRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SeoulUrlRetrofit

    const val portalUrl = "https://api.odcloud.kr/api/"
    const val seoulUrl = "http://openapi.seoul.go.kr:8088/"

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

    @PortalUrlRetrofit
    @Singleton
    @Provides
    fun providePortalRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(portalUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @SeoulUrlRetrofit
    @Singleton
    @Provides
    fun provideSeoulRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(seoulUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideApiService(
        @PortalUrlRetrofit retrofit: Retrofit
    ): SubwayFacilitiesService =
        retrofit.create(SubwayFacilitiesService::class.java)

    @Provides
    @Singleton
    fun provideSubWayTelService(
        @SeoulUrlRetrofit retrofit: Retrofit ,
    ): SubWayTelService =
            retrofit.create(SubWayTelService::class.java)
}