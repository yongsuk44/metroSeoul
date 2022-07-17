import com.google.protobuf.gradle.*

plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.BASE_GRADLE_PLUGIN)
    id("com.google.protobuf") version "0.8.17"
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

    kapt(AndroidLibraries.roomKapt)
    api(AndroidLibraries.roomRuntime)
    implementation(AndroidLibraries.roomKtx)
    implementation(AndroidLibraries.dataStore)
    implementation(AndroidLibraries.dataStoreJava)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${Versions.dataStoreProtoVersion}"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins{
                create("java") {
                    option("lite")
                }
            }
        }
    }
}