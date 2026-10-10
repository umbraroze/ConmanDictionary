plugins {
    java
    kotlin("jvm")
}

group = "org.beastwithin.conmandictionary"
version = "2.2-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.5")
    implementation("org.glassfish.jaxb:jaxb-runtime:4.0.9")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(25)
}

/*
tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs = listOf("-Xlint:deprecation")
}
*/