package com.henriquevieira.notes.base.activity

import androidx.fragment.app.FragmentActivity
import com.henriquevieira.core.router.Router
import javax.inject.Inject

abstract class BaseActivity : FragmentActivity() {
    @Inject
    lateinit var router: Router
}