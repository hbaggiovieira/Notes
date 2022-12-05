plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
        compileSdk = Config.compileSdkVersion
        buildToolsVersion = Config.buildToolsVersion
        applicationId = "br.henriquevieira.notes"
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = Config.androidTestInstrumentation
        vectorDrawables.useSupportLibrary = true
    }

//    productFlavors {
//        create(Config.Brand.brand_a) {
//            dimension = Config.FlavorDimensions.flavor_a
//        }
//
//        create(Config.Brand.brand_b) {
//            dimension = Config.FlavorDimensions.flavor_b
//        }
//    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        kapt {
            correctErrorTypes = true
        }
    }

    buildFeatures.apply {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")

    implementation("androidx.appcompat:appcompat:${Versions.appcompat}")
    implementation("androidx.core:core-ktx:${Versions.coreKtx}")

    val composeBom = platform("androidx.compose:compose-bom:2022.10.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Material Design 3
    implementation("androidx.compose.material3:material3")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.6.1")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    //DI - Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    implementation("androidx.hilt:hilt-compiler:1.0.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle_version}")

    // Annotation processor
    kapt("androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle_version}")

    // optional - Test helpers for LiveData
    testImplementation("androidx.arch.core:core-testing:${Versions.arch_version}")

    // optional - Test helpers for Lifecycle runtime
    testImplementation("androidx.lifecycle:lifecycle-runtime-testing:${Versions.lifecycle_version}")

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:${Versions.arch_version}")
    testImplementation("androidx.lifecycle:lifecycle-runtime-testing:${Versions.lifecycle_version}")

    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}