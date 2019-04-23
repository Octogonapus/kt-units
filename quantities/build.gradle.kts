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

sourceSets {
    main {
        java {
            srcDirs("build/generated/source/kapt/main", "build/generated/source/kaptKotlin/main")
        }
    }
}
