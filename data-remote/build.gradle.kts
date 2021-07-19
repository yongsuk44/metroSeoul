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

    kotlinOptions {
        jvmTarget = Versions.jvmTarget
        languageVersion = Versions.kotlinLanguageVersion
    }
}

dependencies {
    implementation(project(Modules.domain))
    implementation(project(Modules.base))

    implementationList(LibraryList.RetrofitLibraries)
    implementationList(LibraryList.HiltLibraries)
    kaptList(LibraryList.HiltLibraryKapt)

    testImplementationList(LibraryList.mockitoLibrary)
    testImplementationList(LibraryList.TestLibrary)
    testImplementation(Libraries.coroutineTest)
}