package com.henriquevieira.notes.features.checklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.henriquevieira.notes.features.checklist.mvi.CheckListAction
import com.henriquevieira.notes.features.checklist.mvi.CheckListState
import com.henriquevieira.notes.features.checklist.viewmodel.CheckListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CheckListViewModelTest {
    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel: CheckListViewModel = CheckListViewModel()

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
    fun `Given Action CheckBoxClick then isChecked must be changed`() {
        val testItem = CheckListState.Item(
            id = 0,
            isChecked = true,
            content = "Test content"
        )
        runBlocking {
            viewModel.dispatch(
                CheckListAction.ClickCheckBox(
                    selectedItem = testItem
                )
            )
        }

        val list = viewModel.uiState.value.itemsList
        val checkedItem = list?.find { it.id == testItem.id }

        Truth.assertThat(checkedItem?.isChecked).isTrue()
    }
}