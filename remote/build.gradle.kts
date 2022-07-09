plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.kotlinAndroid)
    id(GradlePluginId.kotlinKapt)
    id(GradlePluginId.kotlinAndroidExtensions)
    id(GradlePluginId.hilt)
}

android{

    kotlinOptions {
        jvmTarget = Versions.jvmTarget
        languageVersion = Versions.kotlinLanguageVersion
    }
}

dependencies {
    implementation(project(Modules.base))
    implementation(project(Modules.data))

    implementationList(LibraryList.RetrofitLibraries)
    implementationList(LibraryList.HiltLibraries)
    kaptList(LibraryList.HiltLibraryKapt)

    androidTestImplementation("org.mockito:mockito-android:3.10.0")
    testImplementationList(LibraryList.mockitoLibrary)
    testImplementation(Libraries.coroutineTest)
}