plugins {
    id("java")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation("org.kordamp.ikonli:ikonli-core:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-swing:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-materialdesign-pack:12.3.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

apply(plugin = "com.github.johnrengelman.shadow")

application {
    mainClass = "Main"
}

tasks {
    shadowJar {
        manifest {
            attributes["Main-Class"] = "Main"  // Fully qualified name if in a package
        }

        archiveFileName.set("Lab5.jar")
    }
}
tasks.test {
    useJUnitPlatform()
}
