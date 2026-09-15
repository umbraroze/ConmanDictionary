plugins {
    id("java")
}

group = "org.beastwithin.conmandictionary"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Source: https://mvnrepository.com/artifact/jakarta.xml.bind/jakarta.xml.bind-api
    runtimeOnly("jakarta.xml.bind:jakarta.xml.bind-api:4.0.5")
    // Source: https://mvnrepository.com/artifact/org.openjfx/javafx
    runtimeOnly("org.openjfx:javafx:26.0.2")
    // Source: https://mvnrepository.com/artifact/org.openjfx/javafx-base
    runtimeOnly("org.openjfx:javafx-base:26.0.2")
    // Source: https://mvnrepository.com/artifact/org.openjfx/javafx-controls
    runtimeOnly("org.openjfx:javafx-controls:26.0.2")
    // Source: https://mvnrepository.com/artifact/org.openjfx/javafx-graphics
    runtimeOnly("org.openjfx:javafx-graphics:26.0.2")
    // Source: https://mvnrepository.com/artifact/org.openjfx/javafx-fxml
    runtimeOnly("org.openjfx:javafx-fxml:26.0.2")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}