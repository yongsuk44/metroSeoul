plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.kotlinAndroid)
    id(GradlePluginId.kotlinKapt)
    id(GradlePluginId.kotlinAndroidExtensions)
    id(GradlePluginId.hilt)
}

android{
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        minSdkVersion(AppConfig.minSdk)
        maxSdkVersion(AppConfig.targetSdk)
        targetSdkVersion(AppConfig.targetSdk)
    }

    kotlinOptions {
        jvmTarget = Versions.jvmTarget
        languageVersion = Versions.kotlinLanguageVersion
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(project(Modules.base))
    implementation(project(Modules.data))

    implementation(AndroidLibraries.kotlinReflection)
    implementationList(LibraryList.HiltLibraries)
    kaptList(LibraryList.HiltLibraryKapt)

    kapt(AndroidLibraries.roomKapt)
    api(AndroidLibraries.roomRuntime)
    implementation(AndroidLibraries.roomKtx)
    androidTestImplementationList(LibraryList.AndroidTestLibrary)
    testImplementationList(LibraryList.AndroidTestLibrary)
}