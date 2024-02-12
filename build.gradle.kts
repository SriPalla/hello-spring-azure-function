import com.microsoft.azure.plugin.functions.gradle.task.PackageTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.microsoft.azure.azurefunctions") version "1.8.0"
    id("org.springframework.boot") version "3.0.1"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17

}
repositories {
    mavenLocal()
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
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.4.4")
    compileOnly("org.springframework.cloud:spring-cloud-starter-function-web:3.1.2")
}

group = "com.example"
version = "1.0.0-SNAPSHOT"
description = "Hello Spring Function on Azure"

tasks.withType<Jar> {
    enabled = false
}

tasks.withType<BootJar> {
    mainClass = "com.example.DemoApplication"
}

tasks.withType<PackageTask> {
    dependsOn("bootJar")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
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
        put("key", "value")
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
