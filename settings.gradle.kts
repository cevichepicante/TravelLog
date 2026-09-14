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
        maven { url = uri("https://repository.map.naver.com/archive/maven") }
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication { create<BasicAuthentication>("basic") }
            credentials {
                username = "mapbox"
                password = providers.gradleProperty("MAPBOX_DOWNLOADS_TOKEN").orElse("").get()
            }
        }
    }
}

rootProject.name = "TravelLog"

include(":app")
include(":feature:map")
include(":feature:records")
include(":feature:schedule")
include(":feature:profile")
include(":feature:add-record")
include(":core:data")
include(":core:model")
include(":core:ui")
include(":core:network")
include(":core:platform")
