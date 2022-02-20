import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("org.jetbrains.compose") version "1.0.1"
}

group = "tech.snicmakino"
version = "1.0"

tasks {
    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
    }
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("com.arkivanov.decompose:decompose:0.5.0")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:0.5.0")
    implementation("com.arkivanov.decompose:decompose:0.5.0")
    implementation("com.arkivanov.mvikotlin:mvikotlin:3.0.0-beta01")
    implementation("com.arkivanov.mvikotlin:mvikotlin-main:3.0.0-beta01")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-reaktive:3.0.0-beta01")
    implementation("com.badoo.reaktive:reaktive:1.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation("aws.sdk.kotlin:aws-core-jvm:0.12.0-beta")
    implementation("aws.sdk.kotlin:ec2:0.12.0-beta")

    testImplementation("io.kotest:kotest-runner-junit5:5.1.0")
    testImplementation("com.badoo.reaktive:reaktive-testing:1.2.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "tech.snicmakino.awsmanager.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "aws-manager"
            packageVersion = "1.0.0"
        }
    }
}
