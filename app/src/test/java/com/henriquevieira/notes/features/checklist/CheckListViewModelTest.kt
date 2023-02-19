package com.henriquevieira.notes.features.checklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.henriquevieira.notes.features.checklist.data.model.CheckListItem
import com.henriquevieira.notes.features.checklist.domain.CheckListRepository
import com.henriquevieira.notes.features.checklist.domain.CheckListUseCase
import com.henriquevieira.notes.features.checklist.mvi.CheckListAction
import com.henriquevieira.notes.features.checklist.mvi.CheckListResult
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
    fun `WHEN Action Init with success THEN list must be fetched from database`() {
        val testList = listOf(CheckListItem(id = 0), CheckListItem(id = 1), CheckListItem(id = 2))

        coEvery { repository.getCheckList() }.coAnswers { flowOf(testList) }

        runBlocking { viewModel.dispatch(CheckListAction.Init) }

        Truth.assertThat(viewModel.uiState.value.itemsList).isNotEmpty()
    }

    @Test
    fun `WHEN Action Init with error THEN screen result OnError must be emitted`() {
        val screenFlow = viewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        coEvery { repository.getCheckList() }.throws(Exception())

        runBlocking { viewModel.dispatch(CheckListAction.Init) }

        val screen = screenFlow.replayCache.last()
        Truth.assertThat(screen).isEqualTo(CheckListResult.OnError("Fetch error"))
    }

    @Test
    fun `WHEN Action ToggleCheck THEN isChecked must be changed`() {
        val testItem = CheckListItem(id = 0, isChecked = false)

        coEvery { repository.getCheckList() }.coAnswers { flowOf(listOf(testItem)) }

        runBlocking {
            viewModel.dispatch(CheckListAction.Init)
            viewModel.dispatch(CheckListAction.ToggleCheck(index = 0, isChecked = true))
        }

        val result = viewModel.uiState.value.itemsList.find { it.id == testItem.id }?.isChecked

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `WHEN Action DeleteItem THEN rem item from list`() {
        val testItem = CheckListItem(id = 0)

        repository.apply {
            coEvery { repository.getCheckList() }.coAnswers {
                flowOf(
                    listOf(
                        testItem,
                        testItem.copy(id = 1)
                    )
                )
            }
            coEvery { deleteItem(testItem) }.coAnswers { }
        }

        runBlocking {
            viewModel.dispatch(CheckListAction.Init)
            viewModel.dispatch(CheckListAction.DeleteItem(testItem.id))
        }

        Truth.assertThat(viewModel.uiState.value.itemsList.contains(testItem)).isFalse()
        Truth.assertThat(viewModel.uiState.value.itemsList.contains(testItem.copy(id = 1))).isTrue()
    }

    @Test
    fun `WHEN Action OpenAddItem THEN screen result OnOpenAddItem must be emitted`() {
        val screenFlow = viewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking { viewModel.dispatch(CheckListAction.OpenAddItem) }

        val screen = screenFlow.replayCache.last()
        Truth.assertThat(screen).isEqualTo(CheckListResult.OnOpenAddItem)
    }

    @Test
    fun `WHEN Action AddItem THEN add item to list`() {
        runBlocking { viewModel.dispatch(CheckListAction.AddItem("Test item")) }

        Truth.assertThat(viewModel.uiState.value.itemsList).isNotEmpty()
    }

    @Test
    fun `WHEN Action Close THEN screen result OnClose must be emitted`() {
        val screenFlow = viewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking { viewModel.dispatch(CheckListAction.Close) }

        val screen = screenFlow.replayCache.last()
        Truth.assertThat(screen).isEqualTo(CheckListResult.OnClose)
    }

    @Test
    fun `WHEN Action Save with success THEN screen result OnSaveSuccess must be emitted`() {
        val testList = listOf(CheckListItem(id = 0), CheckListItem(id = 1), CheckListItem(id = 2))

        val screenFlow = viewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        repository.apply {
            coEvery { getCheckList() }.coAnswers { flowOf(testList) }
            coEvery { replaceDatabase(testList) }.coAnswers {  }
        }

        runBlocking {
            viewModel.dispatch(CheckListAction.Init)
            viewModel.dispatch(CheckListAction.Save)
        }

        val screen = screenFlow.replayCache.last()
        Truth.assertThat(screen).isEqualTo(CheckListResult.OnSaveSuccess)
    }

    @Test
    fun `WHEN Action Save with error THEN screen result OnError must be emitted`() {
        val testList = listOf(CheckListItem(id = 0), CheckListItem(id = 1), CheckListItem(id = 2))

        val screenFlow = viewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        repository.apply {
            coEvery { getCheckList() }.coAnswers { flowOf(testList) }
            coEvery { replaceDatabase(testList) }.throws(Exception())
        }

        runBlocking {
            viewModel.dispatch(CheckListAction.Init)
            viewModel.dispatch(CheckListAction.Save)
        }

        val screen = screenFlow.replayCache.last()
        Truth.assertThat(screen).isEqualTo(CheckListResult.OnError("Save error"))
    }
}