plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.kotlinAndroid)
    id(GradlePluginId.kotlinKapt)
    id(GradlePluginId.kotlinAndroidExtensions)
    id(GradlePluginId.hilt)
}

android {
    android {
        compileSdkVersion(AppConfig.compileSdk)
        buildToolsVersion(AppConfig.buildToolsVersion)
    }

    kotlinOptions {
        jvmTarget = Versions.jvmTarget
        languageVersion = Versions.kotlinLanguageVersion
    }
}


dependencies {
    implementation(project(Modules.domain))
    implementation(project(Modules.base))
    implementation(AndroidLibraries.lifecycleViewModel)
    implementation(AndroidLibraries.lifecycleLiveData)
    implementation(AndroidLibraries.lifecycleExtensions)

    implementation(platform(googleCloudeService.googleBom))
    implementationList(LibraryList.firebaseLibrary)

    implementationList(LibraryList.RetrofitLibraries)
    implementationList(LibraryList.HiltLibraries)
    kaptList(LibraryList.HiltLibraryKapt)
    implementation(AndroidLibraries.timber)
    implementation(googleCloudeService.googleService)

    testImplementationList(LibraryList.mockitoLibrary)
    testImplementation(Libraries.coroutineTest)
    testImplementationList(LibraryList.AndroidTestLibrary)
}