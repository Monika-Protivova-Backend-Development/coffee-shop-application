plugins {
    kotlin("jvm") version "1.9.22"
    id("io.ktor.plugin") version "2.3.7"
}

group = "com.motycka.edu"
version = "0.0.1"

// Configure JAR tasks to not include version in the filename
allprojects {
    tasks.withType<Jar> {
        archiveVersion.set("")
    }
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}
