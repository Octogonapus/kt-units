plugins {
    kotlin("kapt")
}

apply {
    plugin("kotlin-kapt")
}

description = "The annotation processor."

val arrowVersion = "0.9.0"

fun DependencyHandler.arrow(name: String) =
    create(group = "io.arrow-kt", name = name, version = arrowVersion)

dependencies {
    implementation(project(":annotation"))
    implementation("com.squareup:kotlinpoet:0.7.0")

    implementation("com.google.auto.service:auto-service:1.0-rc4")
    kapt("com.google.auto.service:auto-service:1.0-rc4")

    implementation(arrow("arrow-core-extensions"))
    implementation(arrow("arrow-typeclasses"))
    implementation(arrow("arrow-extras-extensions"))

    testImplementation(group = "com.natpryce", name = "hamkrest", version = "1.4.2.2")
}
