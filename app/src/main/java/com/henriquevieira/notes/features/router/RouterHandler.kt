package com.henriquevieira.notes.features.router

import android.content.Context
import android.os.Bundle
import com.henriquevieira.core.router.Router
import com.henriquevieira.core.router.Routes
import com.henriquevieira.notes.features.main.MainActivity
import javax.inject.Inject

class RouterHandler @Inject constructor(
    private val context: Context
) : Router {
    override fun navigate(route: Routes, clearTask: Boolean, args: Bundle?) {
        val activity = when(route) {
            is Routes.Main -> MainActivity()
            is Routes.Home -> MainActivity()
        }
    }
}