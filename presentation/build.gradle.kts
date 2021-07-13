plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
}

dependencies {
    implementation(project(Modules.domain))
    implementation(project(Modules.base))
    implementation(AndroidLibraries.lifecycleViewModel)
}