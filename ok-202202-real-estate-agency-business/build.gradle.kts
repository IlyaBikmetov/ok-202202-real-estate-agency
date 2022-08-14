plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}

    sourceSets {
        val kotlinCorVersion: String by project

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            repositories {
                mavenCentral()
                maven { url = uri("https://jitpack.io") }
            }
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(project(":ok-202202-real-estate-agency-common"))
                implementation("com.github.crowdproj.kotlin-cor:kotlin-cor:$kotlinCorVersion")
            }
        }
    }
}