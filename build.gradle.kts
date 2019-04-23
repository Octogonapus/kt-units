import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.30"
}

val annotationProject = project(":annotation")
val processorProject = project(":processor")
val quantitiesProject = project(":quantities")

val kotlinProjects = setOf(
    annotationProject,
    processorProject,
    quantitiesProject
)

allprojects {
    version = "0.1.0-SNAPSHOT"
    group = "org.octogonapus"

    repositories {
        mavenCentral()
        jcenter()
        maven("https://dl.bintray.com/arrow-kt/arrow-kt/")
        maven("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
    }
}

configure(kotlinProjects) {
    apply {
        plugin("kotlin")
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.wrapper {
    gradleVersion = "5.4"
    distributionType = Wrapper.DistributionType.ALL
}
