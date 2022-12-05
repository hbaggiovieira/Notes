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

    implementation("androidx.compose.material3:material3")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    //DI - Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-compiler:1.0.0")

    // Annotation processor
    kapt("androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle_version}")

    // optional - Test helpers for Lifecycle runtime
    kaptTest("com.google.dagger:hilt-android-compiler:2.42")
    kaptTest("com.google.dagger:hilt-compiler:2.42")
    testImplementation("androidx.lifecycle:lifecycle-runtime-testing:${Versions.lifecycle_version}")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:${Versions.arch_version}")
    testImplementation("androidx.lifecycle:lifecycle-runtime-testing:${Versions.lifecycle_version}")

    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation("androidx.compose.ui:ui-tooling")

    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.42")
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}