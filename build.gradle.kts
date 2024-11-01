plugins {
    kotlin("jvm") version "2.0.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "lu.kolja"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("net.minestom:minestom-snapshots:dba90a461b")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.16.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.16.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.16.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")
}
tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "lu.kolja.MainKt"
        }
    }

    test { useJUnitPlatform() }

    build { dependsOn(shadowJar) }

    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
    }
}

kotlin {
    jvmToolchain(21)
}