plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    defaultConfig {
        compileSdk = Config.compileSdkVersion
        buildToolsVersion = Config.buildToolsVersion
        applicationId = Config.applicationId
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = "com.henriquevieira.notes.CustomTestRunner"
        vectorDrawables.useSupportLibrary = true
        signingConfig = signingConfigs.getByName("debug")
    }

//    flavorDimensions += Config.FlavorDimensions.BRAND
//    productFlavors {
//        create(Config.Brand.FREE) {
//            dimension = Config.FlavorDimensions.BRAND
//            applicationIdSuffix = ".${Config.Brand.FREE}"
//            versionNameSuffix = "-${Config.Brand.FREE}"
//            buildConfigField("String", "BRAND", "\"FREE\"")
//        }
//        create(Config.Brand.PREMIUM) {
//            dimension = Config.FlavorDimensions.BRAND
//            applicationIdSuffix = ".${Config.Brand.PREMIUM}"
//            versionNameSuffix = "-${Config.Brand.PREMIUM}"
//            buildConfigField("String", "BRAND", "\"PREMIUM\"")
//        }
//    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    packagingOptions {
        resources.excludes.add("META-INF/*")
        resources.excludes.add("META-INF/DEPENDENCIES")
        resources.excludes.add("META-INF/gradle/*")
    }

    testOptions {
        packagingOptions {
            jniLibs {
                useLegacyPackaging = true
            }
        }
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
        animationsDisabled = true
    }

    tasks.withType<Test> {
        testLogging {
            events("standardOut", "started", "passed", "skipped", "failed")
        }
    }
}

dependencies {
    implementation(project(mapOf("path" to ":commonsui")))
    implementation(project(mapOf("path" to ":core")))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")

    implementation("androidx.appcompat:appcompat:${Versions.appcompat}")
    implementation("androidx.core:core-ktx:${Versions.coreKtx}")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    val composeBom = platform("androidx.compose:compose-bom:2022.10.00")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    //Room
    val room_version = "2.4.3"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")


    //DI - Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Annotation processor
    kapt("androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle_version}")

    //Test
    testImplementation("androidx.lifecycle:lifecycle-runtime-testing:${Versions.lifecycle_version}")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:${Versions.arch_version}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("androidx.lifecycle:lifecycle-runtime-testing:${Versions.lifecycle_version}")
    testImplementation("io.mockk:mockk:1.13.2")
    testImplementation("androidx.room:room-testing:$room_version")


    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation("androidx.compose.ui:ui-tooling")

    //Hilt
    // For instrumented tests.
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")
    // ...with Java.
    androidTestImplementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    androidTestImplementation("io.mockk:mockk-android:1.13.2")
    androidTestImplementation("io.mockk:mockk-agent:1.13.2")

    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test:core-ktx:1.5.0")
}