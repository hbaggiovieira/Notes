package com.henriquevieira.notes.features.checklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.henriquevieira.notes.data.model.CheckListItem
import com.henriquevieira.notes.domain.checklist.CheckListRepository
import com.henriquevieira.notes.domain.checklist.CheckListUseCase
import com.henriquevieira.notes.features.checklist.mvi.CheckListAction
import com.henriquevieira.notes.features.checklist.viewmodel.CheckListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CheckListViewModelTest {
    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: CheckListRepository = mockk()

    private val viewModel: CheckListViewModel = CheckListViewModel(CheckListUseCase(repository))

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

    @Test
    fun `WHEN Action CheckBoxClick THEN isChecked must be changed`() {
        val testItem = CheckListItem(id = 5, isChecked = true)

        repository.apply {
            coEvery { getCheckList() }.coAnswers { flowOf(listOf(testItem, testItem.copy(id = 4))) }
            coEvery { saveItem(testItem) }.coAnswers { }
        }

        runBlocking {
            viewModel.dispatch(CheckListAction.ToggleCheck(index = 0, isChecked = true))
        }

        Truth.assertThat(viewModel.uiState.value.itemsList.contains(testItem)).isTrue()
    }

    @Test
    fun `WHEN Action DeleteItem THEN list size must change`() {
        val testItem = CheckListItem(id = 5, isChecked = true)

        repository.apply {
            coEvery { getCheckList() }.coAnswers { flowOf(listOf(testItem, testItem.copy(id = 4))) }
            coEvery { deleteItem(testItem) }.coAnswers { }
        }

        runBlocking {
            viewModel.dispatch(CheckListAction.DeleteItem(testItem))
        }

        Truth.assertThat(viewModel.uiState.value.itemsList.contains(testItem)).isFalse()
    }
}