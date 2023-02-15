package com.henriquevieira.notes.features.checklist.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.notes.base.activity.BaseActivity
import com.henriquevieira.notes.features.checklist.mvi.CheckListResult
import com.henriquevieira.notes.features.checklist.viewmodel.CheckListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckListActivity : BaseActivity() {
    private val viewModel: CheckListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()

        setContent {
            AppTheme {
                CheckListScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    onUiAction = { viewModel.dispatch(action = it) }
                )
            }
        }
    }

    private fun observe() = lifecycleScope.launch {
        viewModel.screen.collect { state ->
            when (state) {
                is CheckListResult.OnCloseButtonClick -> this@CheckListActivity.onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}