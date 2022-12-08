
object Config {
    const val minSdkVersion = 24
    const val targetSdkVersion = 33
    const val compileSdkVersion = 33

    const val buildToolsVersion = "33.0.1"

    const val versionCode = 1
    const val versionName = "0.1"

    const val applicationId = "br.henriquevieira.notes"

    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"

    object FlavorDimensions {
        const val BRAND = "brand"
    }

    object Brand {
        const val FREE = "free"
        const val PREMIUM = "premium"
    }
}