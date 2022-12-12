package com.henriquevieira.notes.features.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.henriquevieira.commonsui.dialog.CustomAlertDialog
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.core.router.Routes
import com.henriquevieira.notes.BuildConfig
import com.henriquevieira.notes.R
import com.henriquevieira.notes.base.activity.BaseActivity
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.extensions.showToast
import com.henriquevieira.notes.features.home.ui.HomeEvents
import com.henriquevieira.notes.features.home.ui.HomeScreen
import com.henriquevieira.notes.features.home.ui.HomeScreenStates
import com.henriquevieira.notes.features.home.viewmodel.HomeViewModel
import com.henriquevieira.notes.toggle.FeatureToggleUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    private val isShowDialog = mutableStateOf(false)

    private val note = mutableStateOf(Note())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()

        homeViewModel.dispatch(HomeEvents.FetchData)

        setContent {
            AppTheme {
                HomeScreen(
                    uiState = homeViewModel.uiState.collectAsState().value,
                    onUiEvent = {
                        homeViewModel.dispatch(event = it)
                    }
                )

                AlertDialog()
            }
        }
    }

    private fun observe() = lifecycleScope.launch {
        homeViewModel.screen.collect { state ->
            when (state) {
                is HomeScreenStates.OnCardClick -> {
                    if (FeatureToggleUtils.validateBuildToggle(BuildConfig.FEATURE_MAIN)) {
                        val bundle = bundleOf(SELECTED_NOTE_KEY to state.noteId)
                        router.navigate(route = Routes.Note, args = bundle)

                        finish()
                    }
                }

                is HomeScreenStates.OnAddClick -> {
                    if (FeatureToggleUtils.validateBuildToggle(BuildConfig.FEATURE_MAIN)) {
                        router.navigate(route = Routes.Note)

                        finish()
                    }
                }

                is HomeScreenStates.OnDeleteError -> {
                    showToast(getString(R.string.delete_error_message))
                }
                is HomeScreenStates.OnDeleteSuccess -> {

                    homeViewModel.dispatch(HomeEvents.FetchData)

                    showToast(getString(R.string.delete_success_message))
                }

                is HomeScreenStates.OnFetchError -> {
                    showToast(getString(R.string.fetch_error_message))
                }

                is HomeScreenStates.OnShowAlertDialog -> {
                    isShowDialog.value = true

                    note.value = state.note
                }
            }
        }
    }

    @Composable
    private fun AlertDialog() {
        if (isShowDialog.value) {
            CustomAlertDialog(
                isEnabled = isShowDialog,
                title = stringResource(R.string.delete_note_dialog_title),
                text = stringResource(R.string.delete_note_dialog_text),
                confirmButtonLabel = stringResource(R.string.delete_note_dialog_confirm_button_label),
                dismissButtonLabel = stringResource(R.string.delete_note_dialog_cancel_button_label),
                onConfirmClick = {
                    homeViewModel.dispatch(HomeEvents.DeleteConfirm(note.value))
                    isShowDialog.value = false
                },
                onDismissClick = {
                    isShowDialog.value = false
                }
            )
        }
    }

    companion object {
        private const val SELECTED_NOTE_KEY = "selectedNote"
    }
}