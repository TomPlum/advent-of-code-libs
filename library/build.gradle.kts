val releaseVersion: String? by ext

group = "io.github.tomplum"
version = releaseVersion.toString()

dependencies {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.18.2")

    testImplementation(project(":advent-of-code-test-support"))
}