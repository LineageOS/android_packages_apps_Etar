import org.gradle.api.initialization.resolve.RepositoriesMode

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://raw.githubusercontent.com/lineage-next/gradle-generatebp/v1.25/.m2")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Etar-Calendar"
include(":app")
