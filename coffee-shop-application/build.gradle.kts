plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
}

group = "com.motycka.edu"
version = "0.0.1"

// Configure JAR tasks to not include version in the filename
tasks.withType<Jar> {
    archiveVersion.set("")

    // Ensure the main class is properly set in the manifest
    manifest {
        attributes(
            mapOf(
                "Main-Class" to "com.motycka.edu.ApplicationKt"
            )
        )
    }
}

// Configure the Ktor plugin
ktor {
    fatJar {
        archiveFileName.set("coffee-shop-application-all.jar")
    }
}

application {
    mainClass.set("com.motycka.edu.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.logback.classic)
    implementation(libs.kotlin.logging)

    // Exposed dependencies
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.java.time)

    // H2 database
    implementation(libs.h2)

    // Testing dependencies
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation("io.mockk:mockk:1.13.9")
    testImplementation(libs.ktor.server.test.host)
}

kotlin {
    jvmToolchain(21)
}

// Create a task for unit tests
val unitTest = task<Test>("unitTest") {
    description = "Runs unit tests."
    group = "verification"

    // Use the same test classes as the regular test task
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath

    useJUnitPlatform()
}

// Configure the standard test task to run unit tests
tasks.test {
    useJUnitPlatform()

    // Make the test task depend on the unitTest task
    dependsOn(unitTest)
}
