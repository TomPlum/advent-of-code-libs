val releaseVersion: String? by ext

group = "io.github.tomplum"
version = releaseVersion.toString()

dependencies {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.18.2")

    testImplementation(project(":advent-of-code-test-support"))
    testImplementation("io.mockk:mockk:1.13.13")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.28.1")
}