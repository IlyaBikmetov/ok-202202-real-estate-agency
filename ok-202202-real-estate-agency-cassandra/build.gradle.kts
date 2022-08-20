plugins {
    kotlin("jvm")
    kotlin("kapt" )
    kotlin("plugin.serialization")
}

dependencies {
    val coroutinesVersion: String by project
    val cassandraDriverVersion: String by project
    val logbackVersion: String by project
    val kotlinLoggingJvmVersion: String by project
    val kmpUUIDVersion: String by project
    val kotlinSerialization: String by project

    implementation(project(":ok-202202-real-estate-agency-common"))

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")

    implementation("com.benasher44:uuid:$kmpUUIDVersion")

    implementation("com.datastax.oss:java-driver-core:$cassandraDriverVersion")
    implementation("com.datastax.oss:java-driver-query-builder:$cassandraDriverVersion")
    kapt("com.datastax.oss:java-driver-mapper-processor:$cassandraDriverVersion")
    implementation("com.datastax.oss:java-driver-mapper-runtime:$cassandraDriverVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerialization")

    // log
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

    testImplementation(kotlin("test-junit"))
}