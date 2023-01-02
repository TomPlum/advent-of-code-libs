val releaseVersion: String? by ext

group = "io.github.tomplum"
version = releaseVersion.toString()

dependencies {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.0")
    testImplementation("io.mockk:mockk:1.13.3")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
}