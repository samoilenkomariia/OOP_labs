plugins {
    id("java")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

apply(plugin = "com.github.johnrengelman.shadow")

application {
    mainClass = "Lab6"
}

tasks {
    shadowJar {
        manifest {
            attributes["Main-Class"] = "Lab6"
        }

        archiveFileName.set("Lab6.jar")
    }
}

tasks.test {
    useJUnitPlatform()
}