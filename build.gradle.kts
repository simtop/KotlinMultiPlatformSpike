buildscript {
    val sql_delight_version = "1.4.4"
    val kotlin_version = "1.5.10"
    val gradle_version = "7.1.0-alpha01"

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.android.tools.build:gradle:$gradle_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        classpath("com.squareup.sqldelight:gradle-plugin:$sql_delight_version")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}