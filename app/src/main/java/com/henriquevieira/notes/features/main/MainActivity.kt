package com.henriquevieira.notes.features.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.notes.features.main.ui.MainScreen
import com.henriquevieira.notes.features.main.viewmodel.MainScreenEvent
import com.henriquevieira.notes.features.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen(
                    uiState = mainViewModel.uiState.collectAsState().value,
                    onUiEvent = {
                        mainViewModel.dispatch(event = it)
                    }
                )
            }
        }

        mainViewModel.onCreate()

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            mainViewModel.screen.collect {
                when (it) {
                    is MainScreenEvent.OnPrimaryColorSelected -> {
                        showToast("Primary Color")
                    }
                    is MainScreenEvent.OnRedColorSelected -> {
                        showToast("Red Color")
                    }
                    is MainScreenEvent.OnGreenColorSelected -> {
                        showToast("Green Color")
                    }
                    is MainScreenEvent.OnYellowColorSelected -> {
                        showToast("Yellow Color")
                    }
                    is MainScreenEvent.OnBlueColorSelected -> {
                        showToast("Blue Color")
                    }
                    is MainScreenEvent.OnClickClearButton -> {
                        showToast("Clear")
                    }
                    is MainScreenEvent.OnClickSaveButton -> {
                        showToast("Saving")
                    }
                    is MainScreenEvent.OnSaveSuccess -> {
                        showToast("Save Success")
                    }
                    is MainScreenEvent.OnSaveError -> {
                        showToast("Save Error")
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
            .show()
    }
}