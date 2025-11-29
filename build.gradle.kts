plugins {
    kotlin("jvm") version "2.2.21"
    id("org.jetbrains.intellij.platform") version "2.10.5"
}

group = "day.vitayuzu"

kotlin {
    jvmToolchain(21)
}

intellijPlatform {
    buildSearchableOptions = false
    pluginConfiguration {
        id = "day.vitayuzu.tabout"
        name = "Tabout"
        version = "2.0"
        vendor {
            name = "Yuzu Vita"
            email = "github@vitayuzu.day"
            url = "https://github.com/heddxh/tabout"
        }
    }
    pluginVerification {
        ides {
            recommended()
        }
    }
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdea("2025.2.4")
    }
}