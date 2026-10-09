plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "org.beastwithin.conmandictionary"
version = "2.2-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}