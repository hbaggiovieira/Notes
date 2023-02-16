package com.henriquevieira.notes.base.activity

import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.henriquevieira.core.router.Router
import javax.inject.Inject

abstract class BaseActivity : FragmentActivity() {
    @Inject
    lateinit var router: Router

    fun lockScreen(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}