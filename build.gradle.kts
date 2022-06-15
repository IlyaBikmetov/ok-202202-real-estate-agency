plugins {
    kotlin("jvm")
}

group = "ru.ibikmetov.kotlin.realestateagency"
version = "1.0"

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}