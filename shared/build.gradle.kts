import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("com.squareup.sqldelight")
}

version = "1.0"

val coroutine_version = "1.4.0"
val ktor_version = "1.5.3"
val moko_mvvm_version = "0.10.0"
val mockk_version = "1.9.3"
val sql_delight_version = "1.4.4"
val kotlin_serialization_version = "1.2.2"
val koin_version = "3.0.2"

val sqlDelightVersion: String by project
val kotlinVersion: String by project


kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        frameworkName = "shared"
        podfile = project.file("../iosApp/Podfile")
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib-common")

                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")

                // MOKO - MVVM
                implementation("dev.icerock.moko:mvvm:$moko_mvvm_version")

                // KTOR
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-json:$ktor_version")
                implementation("io.ktor:ktor-client-logging:$ktor_version")
                implementation("io.ktor:ktor-client-serialization:$ktor_version")

                // SQL Delight
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")

                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlin_serialization_version")

                implementation("io.insert-koin:koin-core:$koin_version")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")

                // MOKO - MVVM
                implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

                // KTOR
                implementation("io.ktor:ktor-client-android:$ktor_version")

                // SQL Delight
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")

                implementation("io.insert-koin:koin-android:$koin_version")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation ("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")

                // KTOR
                implementation ("io.ktor:ktor-client-ios:$ktor_version")

                // SQL Delight
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
        val iosTest by getting
    }

}

android {
    compileSdk = 30
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 30
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.simtop.shared_db.beers"
    }
}