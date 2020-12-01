buildscript {
  repositories {
    gradlePluginPortal()
    jcenter()
    google()
    mavenCentral()
  }
  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
    classpath("org.jetbrains.kotlin:kotlin-serialization:1.4.20")
    classpath("com.android.tools.build:gradle:4.1.1")
  }
}
group = "com.example.kmmtemplate"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}
