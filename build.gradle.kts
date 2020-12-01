import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        jcenter()
        google()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.4.0"))
    }
}

plugins {
    idea
    kotlin("jvm") version "1.4.0"
}

allprojects {
    group = "io.github.tomplum"
    version = "1.0.0"

    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
        jcenter()
        google()
    }

    dependencies {
        //Standard Libraries
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))

        //Logging
        implementation("org.slf4j:slf4j-api:1.7.30")
        runtimeOnly("org.apache.logging.log4j:log4j-core:2.13.0")

        //Testing
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.2")
        testImplementation("org.junit.platform:junit-platform-launcher:1.3.1")
        testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.20")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}