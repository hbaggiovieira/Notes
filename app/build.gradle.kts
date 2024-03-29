import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.ApplicationDefaultConfig

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
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
        registerFeatureToggles()
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            isShrinkResources = false
            versionNameSuffix = "-debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            customBuildConfigField("FEATURE_MODULE_NAMES",
                FeatureToggleConfig.getDebugFeatureToggles())
        }

        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            versionNameSuffix = "-release"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            customBuildConfigField("FEATURE_MODULE_NAMES",
                FeatureToggleConfig.getReleaseFeatureToggles())
        }
    }

    flavorDimensions += Config.FlavorDimensions.BRAND
    productFlavors {
        create(Config.Brand.FREE) {
            dimension = Config.FlavorDimensions.BRAND
            applicationIdSuffix = ".${Config.Brand.FREE}"
            versionNameSuffix = "-${Config.Brand.FREE}"
        }

        productFlavors {
            create(Config.Brand.PREMIUM) {
                dimension = Config.FlavorDimensions.BRAND
                applicationIdSuffix = ".${Config.Brand.PREMIUM}"
                versionNameSuffix = "-${Config.Brand.PREMIUM}"
            }
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
        kotlinCompilerExtensionVersion = Versions.Compose.kotlinCompiler
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

/**
 * CUSTOM METHODS
 **/
fun ApplicationDefaultConfig.registerFeatureToggles() {
    FeatureToggleConfig.getAllFeatureToggles().forEach {
        buildConfigField("String", it, "\"$it\"")
    }
}

fun ApplicationBuildType.customBuildConfigField(type: String, name: Array<String>) {
    val strValue = name.joinToString(
        prefix = "{",
        separator = ",",
        postfix = "}",
        transform = { "\"$it\"" }
    )
    buildConfigField("String[]", type, strValue)
}


dependencies {
    implementation(project(mapOf("path" to ":commonsui")))
    implementation(project(mapOf("path" to ":core")))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
    implementation("androidx.appcompat:appcompat:${Versions.appcompat}")
    implementation("androidx.core:core-ktx:${Versions.coreKtx}")
    implementation("com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist_systemuicontroller}")

    //AppReview
    implementation("com.google.android.play:review:${Versions.review}")
    implementation("com.google.android.play:review-ktx:${Versions.review}")

    //Firebase
    val firebaseBom = platform("com.google.firebase:firebase-bom:${Versions.Firebase.bom}")
    implementation(firebaseBom)
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics")

    //Compose
    val composeBom = platform("androidx.compose:compose-bom:${Versions.Compose.bom}")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.constraintlayout:constraintlayout-compose:${Versions.Compose.constraintLayout}")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:${Versions.Compose.activity}")
    implementation("androidx.navigation:navigation-compose:${Versions.Compose.navigation}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.viewmodel}")

    //Room
    implementation("androidx.room:room-runtime:${Versions.Room.version}")
    annotationProcessor("androidx.room:room-compiler:${Versions.Room.version}")
    kapt("androidx.room:room-compiler:${Versions.Room.version}")
    implementation("androidx.room:room-ktx:${Versions.Room.version}")


    //DI - Hilt
    implementation("com.google.dagger:hilt-android:${Versions.Hilt.android}")
    implementation("androidx.hilt:hilt-navigation-compose:${Versions.Hilt.navigationCompose}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.Hilt.android}")
    kapt("androidx.hilt:hilt-compiler:${Versions.Hilt.compiler}")

    kapt("androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle_version}")

    //Test
    testImplementation("junit:junit:${Versions.Test.jUnit}")
    testImplementation("androidx.lifecycle:lifecycle-runtime-testing:${Versions.lifecycle_version}")
    testImplementation("androidx.arch.core:core-testing:${Versions.arch_version}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Test.coroutines}")
    testImplementation("com.google.truth:truth:${Versions.Test.truth}")
    testImplementation("androidx.lifecycle:lifecycle-runtime-testing:${Versions.lifecycle_version}")
    testImplementation("io.mockk:mockk:${Versions.Test.mockk}")
    testImplementation("androidx.room:room-testing:${Versions.Room.version}")


    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation("androidx.compose.ui:ui-tooling")

    //Hilt
    androidTestImplementation("com.google.dagger:hilt-android-testing:${Versions.Hilt.android}")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${Versions.Hilt.android}")
    androidTestImplementation("androidx.hilt:hilt-navigation-compose:${Versions.Hilt.navigationCompose}")

    androidTestImplementation("io.mockk:mockk-android:${Versions.Test.mockk}")
    androidTestImplementation("io.mockk:mockk-agent:${Versions.Test.mockk}")

    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test.ext:junit:${Versions.AndroidTest.junit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.AndroidTest.espresso}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    androidTestImplementation("androidx.test:core:${Versions.AndroidTest.coreKtx}")
    androidTestImplementation("androidx.test:core-ktx:${Versions.AndroidTest.coreKtx}")
}