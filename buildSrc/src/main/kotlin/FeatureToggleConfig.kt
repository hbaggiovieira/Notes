import kotlin.reflect.full.memberProperties

object FeatureToggleConfig {
    const val FEATURE_MAIN = "FEATURE_MAIN"
    const val FEATURE_HOME = "FEATURE_HOME"


    fun getAllFeatureToggles() = FeatureToggleConfig::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()

    fun getDebugFeatureToggles(): Array<String> = arrayOf(
        FEATURE_HOME,
        FEATURE_MAIN
    )

    fun getReleaseFeatureToggles(): Array<String> = arrayOf(
        FEATURE_HOME,
        FEATURE_MAIN
    )
}