import Build_gradle.Strings.spotlessLicenseHeaderDelimiter
import Build_gradle.Versions.ktlintVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.30"
    id("com.diffplug.gradle.spotless") version "3.22.0"
    id("org.jlleitschuh.gradle.ktlint") version "7.3.0"
}

val annotationProject = project(":annotation")
val processorProject = project(":processor")
val quantitiesProject = project(":quantities")

val kotlinProjects = setOf(
    annotationProject,
    processorProject,
    quantitiesProject
)

object Versions {
    const val ktlintVersion = "0.29.0"
}

object Strings {
    const val spotlessLicenseHeaderDelimiter = "(@|package|import)"
}

allprojects {
    version = "0.1.0-SNAPSHOT"
    group = "org.octogonapus"

    apply {
        plugin("com.diffplug.gradle.spotless")
    }

    repositories {
        mavenCentral()
        jcenter()
        maven("https://dl.bintray.com/arrow-kt/arrow-kt/")
        maven("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
    }

    spotless {
        /*
         * We use spotless to lint the Gradle Kotlin DSL files that make up the build.
         * These checks are dependencies of the `check` task.
         */
        kotlinGradle {
            ktlint(ktlintVersion)
            trimTrailingWhitespace()
        }
        freshmark {
            trimTrailingWhitespace()
            indentWithSpaces(2)
            endWithNewline()
        }
        format("extraneous") {
            target("src/**/*.fxml")
            trimTrailingWhitespace()
            indentWithSpaces(2)
            endWithNewline()
        }
    }
}

configure(kotlinProjects) {
    apply {
        plugin("kotlin")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    spotless {
        kotlin {
            ktlint(ktlintVersion)
            trimTrailingWhitespace()
            indentWithSpaces(2)
            endWithNewline()
            @Suppress("INACCESSIBLE_TYPE")
            licenseHeaderFile(
                "${rootProject.rootDir}/config/spotless/kt-units.license",
                spotlessLicenseHeaderDelimiter
            )
        }
    }
}

tasks.wrapper {
    gradleVersion = "5.4"
    distributionType = Wrapper.DistributionType.ALL
}
