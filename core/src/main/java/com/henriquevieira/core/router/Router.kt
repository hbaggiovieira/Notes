package com.henriquevieira.core.router

import android.os.Bundle

interface Router {
    fun navigate(route: Routes, clearTask: Boolean = false, args: Bundle? = null)
}