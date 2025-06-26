@file:OptIn(ExperimentalWasmDsl::class)

import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

group = "io.github.bitlinker.kradiobrowser"
version = "1.0.1"

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.dokka)
    alias(libs.plugins.publish)
    alias(libs.plugins.kover)
}

kotlin {
    jvm()
    wasmJs {
        browser()
        nodejs()
    }
    macosX64()
    macosArm64()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.ktor.client.core)
            api(libs.kotlin.datetime)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.client.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.ktor.client.mock)
            implementation(libs.kotlin.coroutines.test)
        }
    }

    explicitApi()
}

android {
    namespace = group.toString()
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

buildkonfig {
    packageName = group.toString()
    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "projectName", project.name)
        buildConfigField(FieldSpec.Type.STRING, "projectVersion", version.toString())
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), name, version.toString())

    pom {
        name = "KRadioBrowser"
        description.set("The Kotlin Multiplatform library for the radio-browser.info API.")
        url.set("https://github.com/bitlinker/KRadioBrowser")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                name.set("Bitlinker")
                email.set("bitlinker@gmail.com")
                url.set("https://github.com/bitlinker")
            }
        }
        scm {
            connection.set("scm:git:git://github.com/bitlinker/KRadioBrowser.git")
            developerConnection.set("scm:git:ssh://git@github.com/bitlinker/KRadioBrowser.git")
            url.set("https://github.com/bitlinker/KRadioBrowser")
        }
    }
}
