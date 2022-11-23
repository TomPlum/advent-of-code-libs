import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.7.21"))
    }
}

plugins {
    idea
    jacoco
    `maven-publish`
    kotlin("jvm") version "1.7.21"
}

project.tasks.publish {
    enabled = false
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "idea")
    apply(plugin = "jacoco")
    apply(plugin = "maven-publish")

    ext {
        set("releaseVersion", "2.3.0")
    }

    repositories {
        mavenCentral()

    }

    dependencies {
        //Standard Libraries
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))

        //Logging
        implementation("org.slf4j:slf4j-api:2.0.4")
        runtimeOnly("org.apache.logging.log4j:log4j-core:2.19.0")
        runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.19.0")

        //Testing
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.0")
        testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.9.0")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    val sourcesJar by tasks.creating(Jar::class) {
        val sourceSets: SourceSetContainer by project
        from(sourceSets["main"].allJava)
        archiveClassifier.set("sources")
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
            create<MavenPublication>(project.name) {
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
        toolVersion = "0.8.8"
        reportsDirectory.set(file("$buildDir/reports"))
    }

    tasks.jacocoTestReport {
        group = "Reporting"
        description = "Generate Jacoco test coverage report"

        reports {
            xml.required.set(true)
            html.required.set(true)
            csv.required.set(false)
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
}
