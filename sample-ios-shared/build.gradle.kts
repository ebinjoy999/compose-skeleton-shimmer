plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { target ->
        target.binaries.framework {
            baseName = "SharedUI"
            isStatic = true
            // Export skeleton-core APIs
            export(project(":skeleton-core"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":skeleton-core"))  // Use api() to export skeleton-core
            
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
        }
    }
}
