import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  kotlin("multiplatform")

  id("com.android.library")
  id("kotlin-android-extensions")
  id("kotlinx-serialization")
}

group = "com.example.kmmtemplate"
version = "1.0-SNAPSHOT"

val coroutineVersion = "1.3.9"
val serializationVersion = "1.0.1"
val ktorVersion = "1.4.1"

repositories {
  gradlePluginPortal()
  google()
  jcenter()
  mavenCentral()
}
kotlin {
  android()
  ios {
    binaries {
      framework {
        baseName = "shared"
      }
    }
  }
  sourceSets {
    val commonMain by getting {
      dependencies {
        api(kotlin("stdlib-common"))

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")

        implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

        implementation("io.ktor:ktor-client-core:$ktorVersion")
        implementation("io.ktor:ktor-client-serialization:$ktorVersion")
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
        api(kotlin("stdlib-jdk7"))
        api("io.ktor:ktor-client-okhttp:$ktorVersion")
        api("io.ktor:ktor-client-serialization-jvm:$ktorVersion")

        implementation("com.squareup.retrofit2:retrofit:2.9.0")
      }
    }
    val androidTest by getting {
      dependencies {
        implementation(kotlin("test-junit"))
        implementation("junit:junit:4.13.1")
      }
    }
    val iosMain by getting
    val iosTest by getting
  }
}
android {
  compileSdkVersion(30)
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  defaultConfig {
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
}
val packForXcode by tasks.creating(Sync::class) {
  group = "build"
  val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
  val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
  val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
  val framework =
    kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
  inputs.property("mode", mode)
  dependsOn(framework.linkTask)
  val targetDir = File(buildDir, "xcode-frameworks")
  from({ framework.outputDirectory })
  into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)
