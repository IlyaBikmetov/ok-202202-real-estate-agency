plugins {
    kotlin("jvm")
}

dependencies {
    val datetimeVersion: String by project
    val coroutinesVersion: String by project

    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

    implementation(kotlin("stdlib-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}