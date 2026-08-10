plugins {
    id("com.android.application") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
}

android {
    namespace = "com.antichaos.app"

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }

    defaultConfig {
        applicationId "com.antichaos.app"
        minSdk 33
        targetSdk 34
        compileSdk 34
        versionCode 1001
        versionName "1.0.0-MVP"
        applicationName "AntiChaos"
    }

    buildTypes {
        release {
            minSdk 24
            targetSdk 34
            signingConfig signConfigs.release
            signingKey alias signing_key_release
        }
    }
}

dependencies {
    // Compose
    implementation("androidx.compose.ui:ui:1.6.7")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    
    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    
    // Hilt DI
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    
    // Coroutines & Lifecycle
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
}

kapt {
    dependencies.setSourceSets([allSources])
}