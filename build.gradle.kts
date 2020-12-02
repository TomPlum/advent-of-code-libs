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
    `maven-publish`
    kotlin("jvm") version "1.4.0"
}

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

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/tomplum/advent-of-code-libs")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
}