package com.henriquevieira.notes.features.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.henriquevieira.commonsui.ds.AppTheme
import com.henriquevieira.core.router.Routes
import com.henriquevieira.notes.base.activity.BaseActivity
import com.henriquevieira.notes.features.home.ui.HomeScreen
import com.henriquevieira.notes.features.home.ui.HomeScreenStates
import com.henriquevieira.notes.features.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel.onCreate()

        observe()

        setContent {
            AppTheme {
                HomeScreen(
                    uiState = homeViewModel.uiState.collectAsState().value,
                    onUiEvent = {
                        homeViewModel.dispatch(event = it)
                    }
                )
            }
        }
    }

    private fun observe() = lifecycleScope.launch {
        homeViewModel.screen.collect {
            when (it) {
                is HomeScreenStates.OnCardClick -> {
                    val activity = router.getAcitvityByRoute(Routes.Main) ?: HomeActivity()
                    val intent = Intent(this@HomeActivity,
                        activity::class.java)

                    intent.putExtra(SELECTED_NOTE_KEY, it.selectedNote)

                    startActivity(intent)
                }

                is HomeScreenStates.OnAddClick -> {
                    router.navigate(
                        route = Routes.Main
                    )
                }

                is HomeScreenStates.OnFetchSuccess -> {
                    Toast.makeText(this@HomeActivity, "Fetch Success", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val SELECTED_NOTE_KEY = "selectedNote"
    }
}