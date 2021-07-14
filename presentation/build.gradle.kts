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
}


dependencies {
    implementation(project(Modules.domain))
    implementation(project(Modules.base))
    implementation(AndroidLibraries.lifecycleViewModel)
    implementation(AndroidLibraries.lifecycleLiveData)
    implementation(AndroidLibraries.lifecycleExtensions)

    implementationList(LibraryList.RetrofitLibraries)
    implementationList(LibraryList.HiltLibraries)
    kaptList(LibraryList.HiltLibraryKapt)
}