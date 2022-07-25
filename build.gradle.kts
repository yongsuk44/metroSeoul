
buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(GradleClasspath.androidGradlePlugin)
        classpath(GradleClasspath.kotlinGradlePluginClasspath)
        classpath(GradleClasspath.hiltGradlePlugin)
        classpath(GradleClasspath.googleService)
        classpath(GradleClasspath.googleCrashlytics)
        classpath(GradleClasspath.safeArgs)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath(GradleClasspath.javapoet)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://www.jitpack.io")
    }
}

task("clean") {
    delete(rootProject.buildDir)
}
