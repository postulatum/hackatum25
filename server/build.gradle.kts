plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    application
}

group = "postulatum.plantum.plantum"
version = "1.0.0"
application {
    mainClass.set("postulatum.plantum.plantum.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation(libs.ktorServerContentNegotiation)
    implementation(libs.ktorSerialization)
    implementation(libs.ktorServerCors)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
    // MongoDB Kotlin Coroutine Driver (v5.x)
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.2.1")
    // Coroutines core (needed by coroutine driver)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}