package base_plugin

import AndroidLibraries
import AppConfig
import GradlePluginId
import Libraries
import LibraryList
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureDefaultPlugins() = listOf(
    GradlePluginId.kotlinAndroid,
    GradlePluginId.kotlinAndroidExtensions,
    GradlePluginId.kotlinKapt,
    GradlePluginId.hilt
).forEach {
    plugins.apply(it)
}

private typealias AndroidBaseExtension = BaseExtension

internal fun Project.configureAndroidApp() = extensions.getByType<AndroidBaseExtension>().run {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig.apply {
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)

        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "com.young.metro.HiltApplicationTestRunner"
    }

    testOptions.apply {
        unitTests.isReturnDefaultValues = true
        animationsDisabled = true
    }
}

internal fun Project.configureBaseDependencies() = dependencies {
    implementations(*LibraryList.RetrofitLibraries)
    implementations(*LibraryList.HiltLibraries)
    implementations(AndroidLibraries.timber)
    implementations(AndroidLibraries.kotlinReflection)
    implementations(Libraries.coroutineAndroid)
    kapts(*LibraryList.HiltLibraryKapt)

    testImplementations(Libraries.coroutineTest)
    testImplementations(*LibraryList.AndroidTestLibrary)
    testImplementations(*LibraryList.mockitoLibrary)

    implementation(platform(googleCloudeService.googleBom))
    implementations(*LibraryList.firebaseLibrary)
}

fun DependencyHandler.implementation(lib : Dependency) =
    add("implementation", lib)

fun DependencyHandler.kapts(vararg list: String) =
    list.forEach { dependency -> add("kapt", dependency) }

fun DependencyHandler.implementations(vararg list: String) =
    list.forEach { dependency -> add("implementation", dependency) }

fun DependencyHandler.androidTestImplementations(vararg list: String) =
    list.forEach { dependency -> add("androidTestImplementation", dependency) }

fun DependencyHandler.testImplementations(vararg list: String) =
    list.forEach { dependency -> add("testImplementation", dependency) }

fun DependencyHandler.debugImplementations(vararg list: String) =
    list.forEach { dependency -> add("debugImplementation", dependency) }
