plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "io.github.e1turin"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

sourceSets.main {
    java.srcDirs("src/main/java")
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}
