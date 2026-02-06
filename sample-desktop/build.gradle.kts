import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm("desktop") {
        withJava()
    }

    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(project(":skeleton-core"))
                
                implementation(compose.desktop.currentOs)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.ebin.skeleton.sample.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "SkeletonSampleDesktop"
            packageVersion = "1.0.0"
            
            macOS {
                bundleID = "com.ebin.skeleton.sample.desktop"
            }
            
            windows {
                menuGroup = "Skeleton Sample"
                upgradeUuid = "e4eafa67-799c-4332-b28e-f6f01887c8c1"
            }
            
            linux {
                packageName = "skeleton-sample-desktop"
            }
        }
    }
}
