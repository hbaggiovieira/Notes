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

        mainViewModel.dispatch(MainScreenEvent.OnCreate)

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            mainViewModel.screen.collect {
                when (it) {
                    is MainScreenEvent.OnCreate -> {

                    }
                    is MainScreenEvent.OnPrimaryColorSelected -> {

                    }
                    is MainScreenEvent.OnRedColorSelected -> {

                    }
                    is MainScreenEvent.OnGreenColorSelected -> {

                    }
                    is MainScreenEvent.OnYellowColorSelected -> {

                    }
                    is MainScreenEvent.OnBlueColorSelected -> {

                    }
                    is MainScreenEvent.OnClickSaveButton -> {

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