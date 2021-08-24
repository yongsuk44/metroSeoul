plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.kotlinAndroid)
    id(GradlePluginId.kotlinKapt)
    id(GradlePluginId.kotlinAndroidExtensions)
}

android{

    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    kotlinOptions {
        jvmTarget = Versions.jvmTarget
        languageVersion = Versions.kotlinLanguageVersion
    }
}

dependencies {
    implementationList(LibraryList.appLibraries)
    api(AndroidLibraries.paging)

    testImplementation(Libraries.coroutineAndroid)
    testImplementation(Libraries.coroutineTest)
    implementation(AndroidLibraries.kotlinReflection)

    testImplementationList(LibraryList.mockitoLibrary)
    testImplementation(Libraries.coroutineTest)
    testImplementationList(LibraryList.AndroidTestLibrary)
}