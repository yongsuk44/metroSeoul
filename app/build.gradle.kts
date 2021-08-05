import java.io.FileInputStream
import java.util.*

plugins {
    id(GradlePluginId.ANDROID_APP)
    id(GradlePluginId.kotlinAndroid)
    id(GradlePluginId.kotlinKapt)
    id(GradlePluginId.kotlinAndroidExtensions)
    id(GradlePluginId.hilt)
    id(GradlePluginId.googlePluginService)
    id(GradlePluginId.safeArgs)
}

val releaseKeystoreFile = rootProject.file("keystore.properties")
val debugKeystoreFile = file("${projectDir}/debug.keystore")
val properties = Properties()

android {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig.apply {
        applicationId = AppConfig.id

        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)

        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "com.young.metro.HiltTestRunner"

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false

            signingConfig = signingConfigs.create("release") {
                properties.load(FileInputStream(releaseKeystoreFile))
                keyAlias = properties.getProperty("keyAlias")
                keyPassword = properties.getProperty("keyPassword")
                storeFile = file(properties.getProperty("storeFile"))
                storePassword = properties.getProperty("storePassword")
            }

            manifestPlaceholders["appName"] = "서울지하철 편의시설"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"

        }

        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".dev"

            signingConfig = signingConfigs.getByName("debug") {
                properties.load(FileInputStream(debugKeystoreFile))

                keyAlias = "androiddebugkey"
                keyPassword = "android"
                storeFile = debugKeystoreFile
                storePassword = "android"
            }

            manifestPlaceholders["appName"] = "app_dev"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"

        }
    }

    buildFeatures {
        dataBinding = true
    }

    kotlinOptions {
        jvmTarget = Versions.jvmTarget
        languageVersion = Versions.kotlinLanguageVersion
    }


}

dependencies {
    implementation(project(Modules.base))
    implementation(project(Modules.domain))
    implementation(project(Modules.dataremote))
    implementation(project(Modules.presentation))
    implementation(project(Modules.data))

    implementation(platform(googleCloudeService.googleBom))
    implementationList(LibraryList.firebaseLibrary)

    implementation(Libraries.lottie)
    implementation(AndroidLibraries.timber)
    implementationList(LibraryList.appLibraries)
    implementationList(LibraryList.cameraLibrary)
    implementationList(LibraryList.exoLibrary)
    implementationList(LibraryList.RecyclerViewLibraries)
    implementationList(LibraryList.NavigationLibraries)
    implementationList(LibraryList.RetrofitLibraries)
    implementationList(LibraryList.Glide)
    implementationList(LibraryList.HiltLibraries)
    kaptList(LibraryList.HiltLibraryKapt)

    testImplementationList(LibraryList.TestLibrary)
}