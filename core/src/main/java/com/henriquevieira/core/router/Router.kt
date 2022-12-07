package com.henriquevieira.core.router

import android.app.Activity
import android.os.Bundle

interface Router {
    fun navigate(route: Routes, clearTask: Boolean = false, args: Bundle? = null)

    fun getAcitvityByRoute(route: Routes): Activity? = null
}