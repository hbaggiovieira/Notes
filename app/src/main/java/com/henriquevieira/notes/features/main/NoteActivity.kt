package com.henriquevieira.notes.features.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.core.router.Routes
import com.henriquevieira.notes.R
import com.henriquevieira.notes.base.activity.BaseActivity
import com.henriquevieira.notes.extensions.showToast
import com.henriquevieira.notes.features.main.ui.NoteEvents
import com.henriquevieira.notes.features.main.ui.NoteScreen
import com.henriquevieira.notes.features.main.ui.NoteScreenStates
import com.henriquevieira.notes.features.main.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteActivity : BaseActivity() {

    private val noteViewModel: NoteViewModel by viewModels()

    private val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            router.navigate(route = Routes.Home)

            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.onBackPressedDispatcher.addCallback(this, callback)

        val args = intent.getBundleExtra(ARGS)
        val selectedNoteId = args?.getInt(SELECTED_NOTE_KEY)
        selectedNoteId?.let {
            noteViewModel.dispatch(event = NoteEvents.LoadSelectedNote(selectedNoteId))
        }


        setContent {
            AppTheme {
                NoteScreen(
                    uiState = noteViewModel.uiState.collectAsState().value,
                    onUiEvent = {
                        noteViewModel.dispatch(event = it)
                    }
                )
            }
        }


        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            noteViewModel.screen.collect {
                when (it) {
                    is NoteScreenStates.OnSaveSuccess -> {
                        showToast(getString(R.string.save_success_message))
                    }
                    is NoteScreenStates.OnSaveError -> {
                        showToast(getString(R.string.save_error_message))
                    }
                    is NoteScreenStates.OnLoadNoteError -> {
                        showToast(getString(R.string.load_note_error_message))
                    }
                    is NoteScreenStates.OnLoadNoteSuccess -> {

                    }
                }
            }
        }
    }

    companion object {
        private const val ARGS = "ARGS"
        private const val SELECTED_NOTE_KEY = "selectedNote"
    }
}