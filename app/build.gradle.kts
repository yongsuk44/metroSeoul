import base_plugin.androidTestImplementations
import base_plugin.implementations
import java.io.FileInputStream
import java.util.Properties

plugins {
    id(GradlePluginId.ANDROID_APP)
    id(GradlePluginId.BASE_GRADLE_PLUGIN)
    id(GradlePluginId.googlePluginService)
    id(GradlePluginId.safeArgs)
}

val releaseKeystoreFile = rootProject.file("keystore.properties")
val debugKeystoreFile = file("${projectDir}/debug.keystore")
val properties = Properties()
val keyProperties = Properties().apply { load(FileInputStream(rootProject.file("local.properties"))) }

android {
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

            manifestPlaceholders.apply {
                put("appName", "서울지하철 편의시설")
                put("appIcon", "@mipmap/ic_launcher")
                put("cloud_key", keyProperties.getProperty("googleCloudeKey"))
            }

            buildConfigField("String", "seoulKey", keyProperties.getProperty("seoulKey"))
            buildConfigField("String", "trailKey", keyProperties.getProperty("trailKey"))
            buildConfigField("String", "apiKey", keyProperties.getProperty("apiKey"))
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

            manifestPlaceholders.apply {
                put("appName", "서울 지하철 테스트")
                put("appIcon", "@mipmap/ic_launcher")
                put("cloud_key", keyProperties.getProperty("googleCloudeKey"))
            }

            buildConfigField("String", "seoulKey", keyProperties.getProperty("seoulKey"))
            buildConfigField("String", "trailKey", keyProperties.getProperty("trailKey"))
            buildConfigField("String", "apiKey", keyProperties.getProperty("apiKey"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
    implementation(project(Modules.data))
    implementation(project(Modules.domain))
    implementation(project(Modules.remote))
    implementation(project(Modules.presentation))
    implementation(project(Modules.cache))

    implementation(platform(googleCloudeService.googleBom))
    implementations(*LibraryList.firebaseLibrary)

    implementation(Libraries.lottie)
    implementation(AndroidLibraries.timber)
    implementations(*LibraryList.appLibraries)
    implementations(*LibraryList.cameraLibrary)
    implementations(*LibraryList.exoLibrary)
    implementations(*LibraryList.RecyclerViewLibraries)
    implementations(*LibraryList.NavigationLibraries)
    implementations(*LibraryList.Glide)

    debugImplementation(AndroidX.fragmentTest)
    kaptAndroidTest(Libraries.hiltKapt)
    androidTestImplementations(*LibraryList.AndroidTestLibrary)
}