@file:OptIn(ExperimentalWasmDsl::class)

import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

group = "com.github.bitlinker.kradiobrowser"
version = "0.0.1"

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
    compileSdk = 35
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

//tasks.withType<DokkaTask>().configureEach {
//    notCompatibleWithConfigurationCache("https://github.com/Kotlin/dokka/issues/2231")
//}

mavenPublishing {
    coordinates(group.toString(), name, version.toString())
}