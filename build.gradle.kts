plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "de.lostmekka"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("de.lostmekka.discord.minesweepergen.MainKt")
}
