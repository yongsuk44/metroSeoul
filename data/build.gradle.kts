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

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(project(Modules.base))
    implementation(project(Modules.domain))

    implementation(AndroidLibraries.kotlinReflection)
    implementationList(LibraryList.HiltLibraries)
    kaptList(LibraryList.HiltLibraryKapt)

    androidTestImplementation("org.mockito:mockito-android:3.10.0")
    testImplementationList(LibraryList.mockitoLibrary)

    kapt(AndroidLibraries.roomKapt)
    api(AndroidLibraries.roomRuntime)
    implementation(AndroidLibraries.roomKtx)
    androidTestImplementationList(LibraryList.AndroidTestLibrary)
    testImplementationList(LibraryList.AndroidTestLibrary)
}