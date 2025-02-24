@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "oh-my-life"
include(":shared")
include(":compose-ui")
include(":app-android")
include(":app-desktop")
include(":apps:mscore")
