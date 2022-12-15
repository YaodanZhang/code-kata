plugins {
    `kotlin-dsl`
    idea
    kotlin("jvm") version "1.6.21"
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("io.freefair.gradle:lombok-plugin:6.6")
}