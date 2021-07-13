plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
}

dependencies {
    implementationList(LibraryList.appLibraries)
    implementation(AndroidLibraries.kotlinReflection)
    api(AndroidLibraries.paging)
}