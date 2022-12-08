package com.henriquevieira.notes.features.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import com.henriquevieira.core.router.Router
import com.henriquevieira.core.router.Routes
import com.henriquevieira.notes.features.home.HomeActivity
import com.henriquevieira.notes.features.main.MainActivity
import javax.inject.Inject

class RouterHandler @Inject constructor(
    private val context: Context,
) : Router {
    override fun navigate(route: Routes, clearTask: Boolean, args: Bundle?) {
        getAcitvityByRoute(route)?.let {
            val intent = Intent(context, it::class.java)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            if (clearTask) {
                intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK)
            }

            args?.let {
                intent.putExtra(ARGS, args)
            }

            context.startActivity(intent)
        }
    }

    override fun getAcitvityByRoute(route: Routes): Activity? {
        val activity = when (route) {
            is Routes.Main -> MainActivity()
            is Routes.Home -> HomeActivity()
            else -> null
        }

        return activity
    }

    companion object {
        private const val ARGS = "ARGS"
    }
}