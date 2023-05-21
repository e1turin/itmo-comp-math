import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.8.0"
    id("org.jetbrains.compose")
}

group = "io.github.e1turin"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("com.arkivanov.decompose:decompose:2.0.0-alpha-02")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.0.0-alpha-02")
                implementation("com.darkrockstudios:mpfilepicker:1.1.0")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "io.github.e1turin.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "lab-2"
            packageVersion = "1.0.0"
        }
    }
}
