plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "org.beastwithin.conmandictionary"
version = "2.2-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":document"))

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
javafx {
    version = "26.0.2"
    modules("javafx.controls", "javafx.fxml")
}
application {
    mainClass = "org.beastwithin.conmandictionary.HelloFX"
    applicationDefaultJvmArgs = listOf("--enable-native-access=javafx.graphics")
}