import com.microsoft.azure.plugin.functions.gradle.task.PackageTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.spring.dependency-management") version "1.1.0"
    id("com.microsoft.azure.azurefunctions") version "1.15.0"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    maven {
        url = uri("https://repo.spring.io/libs-snapshot-local")
    }
    maven {
        url = uri("https://repo.spring.io/libs-milestone-local")
    }
    maven {
        url = uri("https://repo.spring.io/release")
    }
    maven {
        url = uri("https://repo.maven.apache.org/maven2")
    }
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
    implementation("org.springframework.cloud:spring-cloud-function-adapter-azure:4.1.0")
}

group = "com.example"
version = "1.0.0-SNAPSHOT"
description = "Hello Spring Function on Azure"

tasks.withType<Jar> {
    manifest {
        attributes(
            "Main-Class" to "com.example.DemoApplicationKt"
        )
    }
}

tasks.named<PackageTask>("azureFunctionsPackage") {
    dependsOn(tasks.check)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

azurefunctions {
    subscription = "my-spring-function-service-plan"
    resourceGroup = "my-spring-function-resource-group"
    appName = "my-spring-function"
    pricingTier = "Y1"
    region = "westeurope"
    setRuntime(closureOf<com.microsoft.azure.gradle.configuration.GradleRuntimeConfig> {
        os("Windows")
    })
    setAppSettings(closureOf<MutableMap<String, String>> {
        put("FUNCTIONS_EXTENSION_VERSION", "~4")
    })
    setAuth(closureOf<com.microsoft.azure.gradle.auth.GradleAuthConfig> {
        type = "azure_cli"
    })
    // enable local debug
    // localDebug = "transport=dt_socket,server=y,suspend=n,address=5005"
    setDeployment(closureOf<com.microsoft.azure.plugin.functions.gradle.configuration.deploy.Deployment> {
        type = "run_from_blob"
    })
}
