package com.henriquevieira.notes.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.henriquevieira.notes.domain.NoteRepository
import com.henriquevieira.notes.domain.NoteUseCase
import com.henriquevieira.notes.features.home.viewmodel.HomeViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

class HomeViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: NoteRepository = mockk<NoteRepository>().apply {
        //ToDo setup mock
    }

    private val viewModel: HomeViewModel = HomeViewModel(NoteUseCase(repository))

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    //ToDo Create tests
}