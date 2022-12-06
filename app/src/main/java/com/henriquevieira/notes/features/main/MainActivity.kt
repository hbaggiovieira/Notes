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
                        val a = ""
                    }
                    is MainScreenEvent.OnRedColorSelected -> {
                        val a = ""
                    }
                    is MainScreenEvent.OnGreenColorSelected -> {
                        val a = ""
                    }
                    is MainScreenEvent.OnYellowColorSelected -> {
                        val a = ""
                    }
                    is MainScreenEvent.OnBlueColorSelected -> {
                        val a = ""
                    }
                    is MainScreenEvent.OnClickSaveButton -> {
                        val a = ""
                    }
                    is MainScreenEvent.OnSaveSuccess -> {
                        Toast.makeText(this@MainActivity, "Save Success", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is MainScreenEvent.OnSaveError -> {
                        Toast.makeText(this@MainActivity, "Save Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}