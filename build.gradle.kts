group = "com.eureka.katas"
version = "1.0-SNAPSHOT"
description = "bowling-game"
java.sourceCompatibility = JavaVersion.VERSION_17

plugins {
    java
    id("org.jetbrains.kotlin.jvm") version "1.6.0"
    idea
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.0")
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.3.11")
    testImplementation("pl.pragmatists:JUnitParams:1.1.1")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.1.0")
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.test {
    testLogging {
        events("passed", "skipped", "failed")
    }

    reports {
        html.required.set(true)
    }
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}