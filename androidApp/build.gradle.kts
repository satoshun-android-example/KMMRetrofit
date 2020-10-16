plugins {
    id("com.android.application")
    kotlin("android")

    id("kotlin-android-extensions")
}
group = "com.example.kmmtemplate"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))

    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.example.kmmtemplate.androidApp"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility(1.8)
        targetCompatibility(1.8)
    }
}
