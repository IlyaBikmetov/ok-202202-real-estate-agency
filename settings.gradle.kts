rootProject.name = "ok-202202-real-estate-agency"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val koTestVersion: String by settings
        val openapiVersion: String by settings
        val bmuschkoVersion: String by settings

        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatfrom") version kotlinVersion apply false
        id("io.kotest.multiplatform") version koTestVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false

        // spring
        val springBootVersion: String by settings
        val springDependencyVersion: String by settings
        val springPluginVersion: String by settings

        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyVersion
        id("com.bmuschko.docker-java-application") version bmuschkoVersion
        kotlin("plugin.spring") version springPluginVersion
    }
}

include("ok-202202-real-estate-agency-api-v1")
include("ok-202202-real-estate-agency-common")
include("ok-202202-real-estate-agency-mappers")
include("ok-202202-real-estate-agency-spring")
include("ok-202202-real-estate-agency-business")
include("ok-202202-real-estate-agency-async")
include("ok-202202-real-estate-agency-inmemory")
include("ok-202202-real-estate-agency-cassandra")
include("ok-202202-real-estate-agency-logs")
include("ok-202202-real-estate-agency-logging")