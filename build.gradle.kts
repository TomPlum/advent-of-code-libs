import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.4.0"))
    }
}

plugins {
    idea
    jacoco
    `maven-publish`
    kotlin("jvm") version "1.4.0"
}

group = "io.github.tomplum"
version = "1.6.6"

apply(plugin = "kotlin")

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    //Standard Libraries
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    //Logging
    implementation("org.slf4j:slf4j-api:1.7.30")
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.14.0")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.14.0")

    //Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.2")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.20")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.3.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
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
            artifact(sourcesJar)
        }
    }
}

tasks.test {
    useJUnitPlatform { }
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

jacoco {
    toolVersion = "0.8.5"
    reportsDir = file("$buildDir/reports")
}

tasks.jacocoTestReport {
    group = "Reporting"
    description = "Generate Jacoco test coverage report"

    reports {
        xml.isEnabled = true
        html.isEnabled = true
        csv.isEnabled = false
    }
}

tasks.jacocoTestCoverageVerification  {
    violationRules {
        rule {
            limit {
                minimum = "0.9".toBigDecimal()
            }
        }
    }
}