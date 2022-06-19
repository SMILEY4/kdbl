import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "de.ruegnerlukas"
version = "0.1"

plugins {
    kotlin("jvm") version "1.6.21"
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}