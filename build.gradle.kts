plugins {
    id("java")
}

group = "org.beastwithin.conmandictionary"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":document"))
    // org.beastwithin.conmandictionary.document
    // Source: https://mvnrepository.com/artifact/jakarta.xml.bind/jakarta.xml.bind-api
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.5")
    // Source: https://mvnrepository.com/artifact/org.openjfx/javafx
    implementation("org.openjfx:javafx:26.0.2")
    // Source: https://mvnrepository.com/artifact/org.openjfx/javafx-base
    implementation("org.openjfx:javafx-base:26.0.2")
    // Source: https://mvnrepository.com/artifact/org.openjfx/javafx-controls
    implementation("org.openjfx:javafx-controls:26.0.2")
    // Source: https://mvnrepository.com/artifact/org.openjfx/javafx-graphics
    implementation("org.openjfx:javafx-graphics:26.0.2")
    // Source: https://mvnrepository.com/artifact/org.openjfx/javafx-fxml
    implementation("org.openjfx:javafx-fxml:26.0.2")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}