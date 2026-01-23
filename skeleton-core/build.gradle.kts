plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    id("com.vanniktech.maven.publish") version "0.28.0"
}

android {
    namespace = "com.ebin.skeleton"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    buildFeatures {
        compose = true
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    
    debugImplementation(libs.androidx.ui.tooling)
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}



mavenPublishing {
//    Publish using Sonatype’s new Central Portal, not the old OSSRH servers.”
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(
        groupId = "io.github.ebinjoy999",
        artifactId = "compose-skeleton-shimmer",
        version = "1.0.0"
    )

    pom {
        name.set("Compose Skeleton Shimmer")
        description.set("A lightweight, production-ready Skeleton & Shimmer library for Jetpack Compose")
        url.set("https://github.com/ebinjoy999/compose-skeleton-shimmer")

        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0")
            }
        }

        developers {
            developer {
                id.set("ebinjoy999")
                name.set("Ebin Joy")
                email.set("ebinjoy999@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/ebinjoy999/compose-skeleton-shimmer")
        }
    }
}
