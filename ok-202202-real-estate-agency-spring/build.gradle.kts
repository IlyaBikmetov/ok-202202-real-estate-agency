import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    val coroutinesVersion: String by project
    val springBootVersion: String by project
    val jacksonVersion: String by project
    val koTestVersion: String by project
    val springMockk: String by project

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation(project(":ok-202202-real-estate-agency-api-v1"))
    implementation(project(":ok-202202-real-estate-agency-common"))
    implementation(project(":ok-202202-real-estate-agency-mappers"))
    implementation(project(":ok-202202-real-estate-agency-business"))

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux") // Controller, Service, etc..
    testImplementation("com.ninja-squad:springmockk:$springMockk") // mockking beans
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}