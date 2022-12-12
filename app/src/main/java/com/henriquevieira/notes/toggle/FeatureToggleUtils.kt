package com.henriquevieira.notes.toggle

import com.henriquevieira.notes.BuildConfig

object FeatureToggleUtils {
    fun validateBuildToggle(toggle: String): Boolean {
        return BuildConfig.FEATURE_MODULE_NAMES.contains(toggle)
    }
}