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
    mainClass = "Object3"
}

tasks {
    shadowJar {
        manifest {
            attributes["Main-Class"] = "Object3"
        }

        archiveFileName.set("Object3.jar")
    }
}

tasks.test {
    useJUnitPlatform()
}