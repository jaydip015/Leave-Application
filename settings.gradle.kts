pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()




    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

    }
}

rootProject.name = "Leave Application"
include(":app")
