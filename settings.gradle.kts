import java.io.FileInputStream
import java.util.Properties

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/TSurawanwijit/ExampleLibrary")
            val githubProperties = Properties()
            githubProperties.load(FileInputStream(File(rootDir, "gradle.properties")))
            credentials {
                username = githubProperties["gpr.user"]?.toString() ?: ""
                password = githubProperties["gpr.token"]?.toString() ?: ""
            }
        }
    }
}

rootProject.name = "ExampleLibrary"
include(":app")
include(":libraryA")
