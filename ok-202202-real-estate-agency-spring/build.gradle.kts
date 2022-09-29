import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val bmuschkoVersion: String by project

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.bmuschko.docker-spring-boot-application") version "7.4.0"
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
    val cassandraDriverVersion: String by project

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation(project(":ok-202202-real-estate-agency-api-v1"))
    implementation(project(":ok-202202-real-estate-agency-common"))
    implementation(project(":ok-202202-real-estate-agency-mappers"))
    implementation(project(":ok-202202-real-estate-agency-business"))
    implementation(project(":ok-202202-real-estate-agency-cassandra"))

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux") // Controller, Service, etc..
    testImplementation("com.ninja-squad:springmockk:$springMockk") // mockking beans

    implementation("com.datastax.oss:java-driver-core:$cassandraDriverVersion")
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

docker {
    springBootApplication {
        baseImage.set("adoptopenjdk/openjdk11:alpine-jre")
        maintainer.set("(c) Bikmetov")
        ports.set(listOf(8082))
        jvmArgs.set(listOf("-Dspring.profiles.active=production", "-Xmx2048m"))
    }
}