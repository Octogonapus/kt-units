
import Build_gradle.Strings.spotlessLicenseHeaderDelimiter
import Build_gradle.Versions.ktlintVersion
import com.adarshr.gradle.testlogger.theme.ThemeType
import com.github.spotbugs.SpotBugsTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    jacoco
    kotlin("jvm") version "1.3.30"
    id("com.diffplug.gradle.spotless") version "3.22.0"
    id("org.jlleitschuh.gradle.ktlint") version "7.3.0"
    id("com.github.spotbugs") version "1.7.1"
    id("io.gitlab.arturbosch.detekt") version "1.0.0-RC12"
    id("org.jetbrains.dokka") version "0.9.18"
    id("com.adarshr.test-logger") version "1.6.0"
    idea
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
        plugin("com.adarshr.test-logger")
    }

    repositories {
        mavenCentral()
        jcenter()
        maven("https://dl.bintray.com/arrow-kt/arrow-kt/")
        maven("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
    }

    // Configures the Jacoco tool version to be the same for all projects that have it applied.
    pluginManager.withPlugin("jacoco") {
        // If this project has the plugin applied, configure the tool version.
        jacoco {
            toolVersion = "0.8.3"
        }
    }

    tasks.withType<Test> {
        testLogging {
            showStandardStreams = true
            exceptionFormat = TestExceptionFormat.FULL
        }
    }

    testlogger {
        theme = ThemeType.STANDARD_PARALLEL
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
        plugin("checkstyle")
        plugin("jacoco")
        plugin("com.github.spotbugs")
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jetbrains.dokka")
        plugin("org.gradle.idea")
    }

    dependencies {
        fun junitJupiter(name: String, version: String = "5.2.0") =
            create(group = "org.junit.jupiter", name = name, version = version)

        implementation(kotlin("stdlib-jdk8"))

        testCompile(junitJupiter(name = "junit-jupiter-api"))
        testCompile(junitJupiter(name = "junit-jupiter-engine"))
        testCompile(junitJupiter(name = "junit-jupiter-params"))
        testRuntime(
            group = "org.junit.platform",
            name = "junit-platform-launcher",
            version = "1.0.0"
        )
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    tasks.withType<Test> {
        @Suppress("UnstableApiUsage")
        useJUnitPlatform {
            filter {
                includeTestsMatching("*Test")
                includeTestsMatching("*Tests")
                includeTestsMatching("*Spec")
            }

            /*
             * Performance tests are only really run during development.
             * They don't need to run in CI or as part of regular development.
             */
            excludeTags("performance")

            /*
             * Marking a test as `slow` will excluded it from being run as part of the regular CI system.
             */
            excludeTags("slow")
        }

        if (project.hasProperty("jenkinsBuild") || project.hasProperty("headless")) {
            jvmArgs = listOf(
                "-Djava.awt.headless=true",
                "-Dtestfx.robot=glass",
                "-Dtestfx.headless=true",
                "-Dprism.order=sw",
                "-Dprism.text=t2k"
            )
        }

        testLogging {
            events(
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED,
                TestLogEvent.STARTED
            )
            displayGranularity = 0
            showExceptions = true
            showCauses = true
            showStackTraces = true
            exceptionFormat = TestExceptionFormat.FULL
        }

        @Suppress("UnstableApiUsage")
        reports.junitXml.destination = file("${rootProject.buildDir}/test-results/${project.name}")
    }

    tasks.withType<JacocoReport> {
        @Suppress("UnstableApiUsage")
        reports {
            html.isEnabled = true
            xml.isEnabled = true
            csv.isEnabled = false
        }
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
            targetExclude("${quantitiesProject.buildDir}/**/*")
        }
    }

    detekt {
        toolVersion = "1.0.0-RC12"
        input = files(
            "src/main/kotlin",
            "src/test/kotlin"
        )
        parallel = true
        config = files("${rootProject.rootDir}/config/detekt/config.yml")
    }

    checkstyle {
        toolVersion = "8.1"
    }

    spotbugs {
        toolVersion = "4.0.0-beta1"
        excludeFilter = file("${rootProject.rootDir}/config/spotbugs/spotbugs-excludeFilter.xml")
    }

    tasks.withType<SpotBugsTask> {
        @Suppress("UnstableApiUsage")
        reports {
            xml.isEnabled = false
            emacs.isEnabled = false
            html.isEnabled = true
        }
    }

    idea {
        module {
            sourceDirs.plusAssign(
                files(
                    "${rootProject.rootDir}/quantities/build/generated/source/kapt/main",
                    "${rootProject.rootDir}/quantities/build/generated/source/kaptKotlin/main"
                )
            )
            generatedSourceDirs.plusAssign(
                files(
                    "${rootProject.rootDir}/quantities/build/generated/source/kapt/main",
                    "${rootProject.rootDir}/quantities/build/generated/source/kaptKotlin/main"
                )
            )
        }
    }
}

tasks.dokka {
    dependsOn(tasks.classes)
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

tasks.wrapper {
    gradleVersion = "5.4"
    distributionType = Wrapper.DistributionType.ALL
}

/**
 * Configures the [checkstyle][org.gradle.api.plugins.quality.CheckstyleExtension] project extension.
 */
fun Project.`checkstyle`(configure: org.gradle.api.plugins.quality.CheckstyleExtension.() -> Unit) =
    extensions.configure("checkstyle", configure)
