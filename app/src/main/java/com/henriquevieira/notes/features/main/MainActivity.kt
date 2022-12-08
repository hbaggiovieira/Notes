package com.henriquevieira.notes.features.main

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.features.main.ui.MainEvents
import com.henriquevieira.notes.features.main.ui.MainScreen
import com.henriquevieira.notes.features.main.ui.MainScreenStates
import com.henriquevieira.notes.features.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedNote = intent.extras?.getParcelable(SELECTED_NOTE_KEY, Note::class.java)

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

        mainViewModel.dispatch(event = MainEvents.OnNoteSelected(selectedNote ?: Note()))

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            mainViewModel.screen.collect {
                when (it) {
                    is MainScreenStates.OnSaveSuccess -> {
                        showToast("Save Success")
                    }
                    is MainScreenStates.OnSaveError -> {
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

    companion object {
        private const val SELECTED_NOTE_KEY = "selectedNote"
    }
}