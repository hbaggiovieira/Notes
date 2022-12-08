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
import com.henriquevieira.notes.base.activity.BaseActivity
import com.henriquevieira.notes.data.model.Note
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

        val selectedNoteId = intent.extras?.getInt(SELECTED_NOTE_KEY)

        this.onBackPressedDispatcher.addCallback(this, callback)

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

        selectedNoteId?.let {
            mainViewModel.dispatch(event = MainEvents.OnNoteSelected(selectedNoteId))
        }
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
                    is MainScreenStates.OnFetchError -> {
                        showToast("Fetch error")
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