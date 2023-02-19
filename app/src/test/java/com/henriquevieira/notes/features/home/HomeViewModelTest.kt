package com.henriquevieira.notes.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.henriquevieira.commonsui.textinput.NoteType
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.domain.NoteRepository
import com.henriquevieira.notes.domain.NoteUseCase
import com.henriquevieira.notes.features.home.mvi.HomeAction
import com.henriquevieira.notes.features.home.mvi.HomeResult
import com.henriquevieira.notes.features.home.viewmodel.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: NoteRepository = mockk<NoteRepository>().apply {
        coEvery {
            deleteNote(VALID_TEST_NOTE)
        }.coAnswers { Unit }

        coEvery {
            deleteNote(INVALID_TEST_NOTE)
        }.throws(Exception())
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

    @Test
    fun `When fetchData throws exception THEN OnFetchError must be emitted`() {
        val screenFlow = viewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        repository.apply {
            coEvery {
                getNotes()
            }.throws(Exception())
        }

        runBlocking {
            viewModel.dispatch(HomeAction.FetchData)
        }

        screenFlow.onCompletion {
            val screen = screenFlow.replayCache.last()

            Truth.assertThat(screen).isEqualTo(HomeResult.OnFetchError)
        }
    }

    @Test
    fun `When click add button THEN OnAddClick must be emitted`() {
        val screenFlow = viewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            viewModel.dispatch(HomeAction.AddClick)
        }

        screenFlow.onCompletion {
            val screen = screenFlow.replayCache.last()

            Truth.assertThat(screen).isEqualTo(HomeResult.OnAddClick)
        }
    }

    @Test
    fun `When click card THEN OnCardClick must be emitted`() {
        val screenFlow = viewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            viewModel.dispatch(
                HomeAction.CardClick(
                VALID_TEST_NOTE.id
            ))
        }

        screenFlow.onCompletion {
            val screen = screenFlow.replayCache.last()

            Truth.assertThat(screen).isEqualTo(HomeResult.OnCardClick(VALID_TEST_NOTE.id))
        }
    }

    @Test
    fun `When long press card THEN OnShowAlertDialog must be emitted`() {
        val screenFlow = viewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            viewModel.dispatch(HomeAction.CardLongPress(VALID_TEST_NOTE))
        }

        screenFlow.onCompletion {
            val screen = screenFlow.replayCache.last()

            Truth.assertThat(screen).isEqualTo(HomeResult.OnShowAlertDialog(VALID_TEST_NOTE))
        }
    }

    @Test
    fun `When click dialog confirm button with valid note THEN OnDeleteSuccess must be emitted`() {
        val screenFlow = viewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            viewModel.dispatch(HomeAction.DeleteConfirm(VALID_TEST_NOTE))
        }

        screenFlow.onCompletion {
            val screen = screenFlow.replayCache.last()

            Truth.assertThat(screen).isEqualTo(HomeResult.OnDeleteSuccess)
        }
    }

    @Test
    fun `When click dialog confirm button with invalid note THEN OnDeleteError must be emitted`() {
        val screenFlow = viewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            viewModel.dispatch(HomeAction.DeleteConfirm(INVALID_TEST_NOTE))
        }

        screenFlow.onCompletion {
            val screen = screenFlow.replayCache.last()

            Truth.assertThat(screen).isEqualTo(HomeResult.OnDeleteError)
        }
    }

    companion object {
        private val VALID_TEST_NOTE = Note(
            id = 1,
            title = "Test Title 1",
            contentText = "Test Content Text 1",
            noteType = NoteType.Primary
        )

        private val VALID_TEST_NOTE_2 = Note(
            id = 1,
            title = "Test Title 1",
            contentText = "Test Content Text 1",
            noteType = NoteType.Primary
        )

        private val INVALID_TEST_NOTE = Note()
    }
}