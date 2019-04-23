buildCache {
    local(DirectoryBuildCache::class.java) {
        isEnabled = true
        directory = file("${rootDir.path}/build-cache")
    }
}

rootProject.name = "kt-units"

include(":annotation")
include(":processor")
include(":quantities")
