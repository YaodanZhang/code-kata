plugins {
    idea
    java
    groovy
    id("io.freefair.lombok")
}

group = "yaodan.zhang"
version = "0.1"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")

    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.hamcrest:hamcrest:2.2")

    testImplementation("org.codehaus.groovy:groovy-all:3.0.13")
    testImplementation("org.spockframework:spock-core:2.3-groovy-3.0")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
