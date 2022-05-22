plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":ok-202202-real-estate-agency-api-v1"))
    implementation(project(":ok-202202-real-estate-agency-common"))

    testImplementation(kotlin("test-junit"))
}