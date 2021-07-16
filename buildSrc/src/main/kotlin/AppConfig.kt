object AppConfig {
    const val id = "com.young.metro"

    //버전 코드 기준 -> 1씩 추가
    const val versionCode = 21071406
    const val versionName = "0.1"

    const val compileSdk = 30
    const val minSdk = 24
    const val targetSdk = 30
    const val buildToolsVersion = "30.0.3"
}

object GradlePluginId {
    const val ANDROID_APP = "com.android.application"
    const val ANDROID_LIBRARY = "com.android.library"
    const val FIREBASE_CRASH ="com.google.firebase.crashlytics"

    const val kotlin = "kotlin"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinAndroidExtensions = "kotlin-parcelize"
    const val hilt = "dagger.hilt.android.plugin"

    const val googlePluginService = "com.google.gms.google-services"
}


object GradleClasspath {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val kotlinGradlePluginClasspath =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltCore}"
    const val googleCrashlytics = "com.google.firebase:firebase-crashlytics-gradle:2.5.2"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.2"
    const val googleService = "com.google.gms:google-services:4.3.4"
}
