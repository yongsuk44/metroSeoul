package com.young.remote.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.young.remote.api.PublicDataOpenApiService
import com.young.remote.api.PublicDataPortalApiService
import com.young.remote.api.SeoulApiService
import com.young.remote.api.TrailPorTalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PublicDataOpenApiUrlRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PublicDataPortalUrlRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SeoulUrlRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class trailPortalUrlRetrofit

    const val PublicDataPortalUrl = "https://api.odcloud.kr/api/"
    const val PublicDataOpenApiUrl = "http://api.data.go.kr/openapi/"
    const val seoulUrl = "http://openapi.seoul.go.kr:8088/"
    const val trailPortalUrl = "http://openapi.kric.go.kr/openapi/"

    val NETWORK_TIME_OUT: Long = 10

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

    @PublicDataPortalUrlRetrofit
    @Singleton
    @Provides
    fun providePortalRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(PublicDataPortalUrl)
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

    @trailPortalUrlRetrofit
    @Singleton
    @Provides
    fun provideTrailPortalRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(trailPortalUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @PublicDataOpenApiUrlRetrofit
    @Singleton
    @Provides
    fun provideTrailPortalOpenApiRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(PublicDataOpenApiUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideApiService(
        @PublicDataPortalUrlRetrofit retrofit: Retrofit
    ): PublicDataPortalApiService =
        retrofit.create(PublicDataPortalApiService::class.java)

    @Provides
    @Singleton
    fun provideSubWayTelService(
        @SeoulUrlRetrofit retrofit: Retrofit ,
    ): SeoulApiService =
        retrofit.create(SeoulApiService::class.java)

    @Provides
    @Singleton
    fun provideTrailPortalService(
        @trailPortalUrlRetrofit retrofit: Retrofit,
    ): TrailPorTalService =
        retrofit.create(TrailPorTalService::class.java)


    @Provides
    @Singleton
    fun provideTrailPortalOpenApiService(
        @PublicDataOpenApiUrlRetrofit retrofit: Retrofit,
    ): PublicDataOpenApiService =
        retrofit.create(PublicDataOpenApiService::class.java)
}