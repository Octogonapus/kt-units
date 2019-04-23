plugins {
    kotlin("kapt")
}

apply {
    plugin("kotlin-kapt")
}

description = "The annotation processor."

val arrowVersion = "0.9.0"

fun DependencyHandler.arrow(name: String) =
    implementation(group = "io.arrow-kt", name = name, version = arrowVersion)

dependencies {
    implementation(project(":annotation"))
    implementation("com.squareup:kotlinpoet:0.7.0")

    implementation("com.google.auto.service:auto-service:1.0-rc4")
    kapt("com.google.auto.service:auto-service:1.0-rc4")

    arrow("arrow-core-extensions")
    arrow("arrow-typeclasses")
    arrow("arrow-extras-extensions")
}
