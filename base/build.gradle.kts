plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
}

android{
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)
}

dependencies {
    implementationList(LibraryList.appLibraries)
    api(AndroidLibraries.paging)
}