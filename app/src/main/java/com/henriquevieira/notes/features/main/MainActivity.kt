package com.henriquevieira.notes.features.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.core.router.Routes
import com.henriquevieira.notes.R
import com.henriquevieira.notes.base.activity.BaseActivity
import com.henriquevieira.notes.features.main.ui.MainEvents
import com.henriquevieira.notes.features.main.ui.MainScreen
import com.henriquevieira.notes.features.main.ui.MainScreenStates
import com.henriquevieira.notes.features.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            router.navigate(route = Routes.Home, clearTask = true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.onBackPressedDispatcher.addCallback(this, callback)

        val selectedNoteId = intent.extras?.getInt(SELECTED_NOTE_KEY)
        selectedNoteId?.let {
            mainViewModel.dispatch(event = MainEvents.LoadSelectedNote(selectedNoteId))
        }


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


        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            mainViewModel.screen.collect {
                when (it) {
                    is MainScreenStates.OnSaveSuccess -> {
                        showToast(getString(R.string.save_success_message))
                    }
                    is MainScreenStates.OnSaveError -> {
                        showToast(getString(R.string.save_error_message))
                    }
                    is MainScreenStates.OnLoadNoteError -> {
                        showToast(getString(R.string.load_note_error_message))
                    }
                    is MainScreenStates.OnLoadNoteSuccess -> {

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