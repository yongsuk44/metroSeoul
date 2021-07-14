plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.kotlinAndroid)
    id(GradlePluginId.kotlinKapt)
    id(GradlePluginId.kotlinAndroidExtensions)
    id(GradlePluginId.hilt)
}

android {
    android{
        compileSdkVersion(AppConfig.compileSdk)
        buildToolsVersion(AppConfig.buildToolsVersion)
    }

    kotlinOptions {
        jvmTarget = Versions.jvmTarget
        languageVersion = Versions.kotlinLanguageVersion
    }
}

dependencies {
    implementation(project(Modules.base))
    implementationList(LibraryList.RetrofitLibraries)
    implementation(AndroidLibraries.kotlinReflection)
    testImplementation(Libraries.coroutineAndroid)
    testImplementation(Libraries.coroutineTest)
    implementationList(LibraryList.HiltLibraries)
    kaptList(LibraryList.HiltLibraryKapt)
}