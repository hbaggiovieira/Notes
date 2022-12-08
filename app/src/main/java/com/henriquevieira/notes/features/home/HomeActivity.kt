package com.henriquevieira.notes.features.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import com.henriquevieira.commonsui.dialog.CustomAlertDialog
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.core.router.Routes
import com.henriquevieira.notes.R
import com.henriquevieira.notes.base.activity.BaseActivity
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.features.home.ui.HomeEvents
import com.henriquevieira.notes.features.home.ui.HomeScreen
import com.henriquevieira.notes.features.home.ui.HomeScreenStates
import com.henriquevieira.notes.features.home.viewmodel.HomeViewModel
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
                    val activity = router.getAcitvityByRoute(Routes.Main) ?: HomeActivity()
                    val intent = Intent(this@HomeActivity,
                        activity::class.java)

                    intent.putExtra(SELECTED_NOTE_KEY, state.noteId)

                    startActivity(intent)
                }

                is HomeScreenStates.OnAddClick -> {
                    finish()

                    router.navigate(
                        route = Routes.Main
                    )
                }

                is HomeScreenStates.OnDeleteError -> {
                    Toast.makeText(this@HomeActivity,
                        getString(R.string.delete_error_message),
                        Toast.LENGTH_SHORT).show()
                }
                is HomeScreenStates.OnDeleteSuccess -> {

                    homeViewModel.dispatch(HomeEvents.FetchData)

                    Toast.makeText(this@HomeActivity,
                        getString(R.string.delete_success_message),
                        Toast.LENGTH_SHORT).show()
                }

                is HomeScreenStates.OnFetchError -> {
                    Toast.makeText(this@HomeActivity, getString(R.string.fetch_error_message), Toast.LENGTH_SHORT).show()
                }

                is HomeScreenStates.OnFetchSuccess -> {
                    Unit
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
        } else {
            val a = ""
        }
    }

    companion object {
        private const val SELECTED_NOTE_KEY = "selectedNote"
    }
}