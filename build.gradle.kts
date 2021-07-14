// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(GradleClasspath.androidGradlePlugin)
        classpath(GradleClasspath.kotlinGradlePluginClasspath)
        classpath(GradleClasspath.hiltGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

task("clean") {
    delete(rootProject.buildDir)
}
