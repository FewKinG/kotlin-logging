import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.amazonaws.auth.profile.*

plugins {
    kotlin("jvm") version "1.4.10"
    `java-library`
    `maven-publish`
}

group = "app.haspa.alex"
version = "1.0.0"

val kotlinVersion = "1.4.10"
val log4jVersion = "2.12.1"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    publications {
        create<MavenPublication>("kotlinLogging") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "hn"
            url = uri("s3://maven.paulalex.de/release/")
            credentials(AwsCredentials::class.java) {
                try {
                    val awsCredentials = ProfileCredentialsProvider().credentials
                    accessKey = awsCredentials.getAWSAccessKeyId()
                    secretKey = awsCredentials.getAWSSecretKey()
                } catch (e: Exception) {
                    accessKey = ""
                    secretKey = ""
                }
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api("org.apache.logging.log4j:log4j-core:$log4jVersion")
    api("org.apache.logging.log4j:log4j-api:$log4jVersion")
    api("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")
}

buildscript {
    dependencies {
        classpath("com.amazonaws:aws-java-sdk-core:1.11.725")
    }
}

