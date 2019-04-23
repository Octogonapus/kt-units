plugins {
    kotlin("kapt")
}

apply {
    plugin("kotlin-kapt")
}

description = "Contains definitions of common quantities."

dependencies {
    implementation(project(":annotation"))
    kapt(project(":processor"))
}
