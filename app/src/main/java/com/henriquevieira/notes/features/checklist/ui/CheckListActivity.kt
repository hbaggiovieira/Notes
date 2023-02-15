package com.henriquevieira.notes.features.checklist.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.notes.base.activity.BaseActivity
import com.henriquevieira.notes.features.checklist.viewmodel.CheckListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckListActivity : BaseActivity() {
    private val viewModel: CheckListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                CheckListScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    onUiAction = { viewModel.dispatch(action = it) }
                )
            }
        }
    }
}