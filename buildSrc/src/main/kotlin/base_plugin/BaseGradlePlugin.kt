package base_plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

open class BaseGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configureDefaultPlugins()
        target.configureAndroidApp()
        target.configureBaseDependencies()
    }
}