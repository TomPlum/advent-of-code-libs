val releaseVersion: String? by ext

group = "io.github.tomplum"
version = releaseVersion.toString()

dependencies {
    implementation(project(":advent-of-code-libs"))
    implementation("org.junit.jupiter:junit-jupiter-api:5.11.4")
}