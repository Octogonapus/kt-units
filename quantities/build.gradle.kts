plugins {
    kotlin("kapt")
    idea
}

apply {
    plugin("kotlin-kapt")
}

description = "Contains definitions of common quantities."

dependencies {
    implementation(project(":annotation"))
    kapt(project(":processor"))
}

idea {
    module {
        sourceDirs = sourceDirs + files(
            "build/generated/source/kapt/main",
            "build/generated/source/kaptKotlin/main"
        )

        generatedSourceDirs = generatedSourceDirs + files(
            "build/generated/source/kapt/main",
            "build/generated/source/kaptKotlin/main"
        )
    }
}
