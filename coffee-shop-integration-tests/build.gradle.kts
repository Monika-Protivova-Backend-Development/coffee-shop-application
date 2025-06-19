plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    idea
    application
}

group = "com.motycka.edu"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":coffee-shop-application"))

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.netty)

    // Ktor dependencies
    implementation(libs.ktor.server.test.host)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.cio)

    // Test dependencies
    testImplementation(libs.kotlin.test)

    // Kotest
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.property)

    // Testcontainers
    testImplementation(libs.testcontainers)
    testImplementation(libs.testcontainers.junit.jupiter)
}

// Create a task for integration tests
val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    // Use the same test classes as the regular test task
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath

    useJUnitPlatform()
}

// Configure the standard test task to skip tests in this module
// since all tests in this module are integration tests
tasks.test {
    // This will effectively make the test task do nothing
    // All tests will be run by the integrationTest task
    enabled = false
}

// Add integration tests to the check task
tasks.check {
    dependsOn(integrationTest)
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}
