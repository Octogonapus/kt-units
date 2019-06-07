plugins {
    kotlin("kapt")
}

apply {
    plugin("kotlin-kapt")
}

description = "The annotation processor."

fun DependencyHandler.arrow(name: String) =
    create(group = "io.arrow-kt", name = name, version = property("arrow.version") as String)

dependencies {
    api(project(":annotation"))

    implementation(
        group = "com.squareup",
        name = "kotlinpoet",
        version = property("kotlinpoet.version") as String
    )
    implementation(
        group = "com.google.auto.service",
        name = "auto-service",
        version = property("auto-service.version") as String
    )
    kapt(
        group = "com.google.auto.service",
        name = "auto-service",
        version = property("auto-service.version") as String
    )

    implementation(arrow("arrow-core-extensions"))
    implementation(arrow("arrow-typeclasses"))
    implementation(arrow("arrow-extras-extensions"))

    testImplementation(
        group = "com.natpryce",
        name = "hamkrest",
        version = property("hamkrest.version") as String
    )
}
