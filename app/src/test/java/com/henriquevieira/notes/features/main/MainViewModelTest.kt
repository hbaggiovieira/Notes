package com.henriquevieira.notes.features.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.domain.NoteRepository
import com.henriquevieira.notes.domain.NoteUseCase
import com.henriquevieira.notes.features.main.ui.MainEvents
import com.henriquevieira.notes.features.main.ui.MainScreenStates
import com.henriquevieira.notes.features.main.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: NoteRepository = mockk<NoteRepository>().apply {
        coEvery {
            getNoteById(VALID_TEST_NOTE.id)
        }.coAnswers {
            flowOf(VALID_TEST_NOTE)
        }

        coEvery {
            getNoteById(INVALID_TEST_NOTE.id)
        }.throws(Exception())

        coEvery {
            saveNote(VALID_TEST_NOTE)
        }.coAnswers { Unit }

        coEvery {
            saveNote(INVALID_TEST_NOTE)
        }.throws(Exception())

        coEvery {
            deleteNote(any())
        }.coAnswers { Unit }
    }

    private val mainViewModel = MainViewModel(
        NoteUseCase(repository)
    )

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
    fun `When primary color selected THEN noteType must be Primary`() {
        runBlocking {
            mainViewModel.dispatch(MainEvents.PrimaryColorSelected)
        }

        Truth.assertThat(mainViewModel.uiState.value.note.noteType).isEqualTo(NoteTypes.Primary)
    }

    @Test
    fun `When red color selected THEN noteType must be Red`() {
        runBlocking {
            mainViewModel.dispatch(MainEvents.RedColorSelected)
        }

        Truth.assertThat(mainViewModel.uiState.value.note.noteType).isEqualTo(NoteTypes.Red)
    }

    @Test
    fun `When green color selected THEN noteType must be Green`() {
        runBlocking {
            mainViewModel.dispatch(MainEvents.GreenColorSelected)
        }

        Truth.assertThat(mainViewModel.uiState.value.note.noteType).isEqualTo(NoteTypes.Green)
    }

    @Test
    fun `When yellow color selected THEN noteType must be Yellow`() {
        runBlocking {
            mainViewModel.dispatch(MainEvents.YellowColorSelected)
        }

        Truth.assertThat(mainViewModel.uiState.value.note.noteType).isEqualTo(NoteTypes.Yellow)
    }

    @Test
    fun `When blue color selected THEN noteType must be Blue`() {
        runBlocking {
            mainViewModel.dispatch(MainEvents.BlueColorSelected)
        }

        Truth.assertThat(mainViewModel.uiState.value.note.noteType).isEqualTo(NoteTypes.Blue)
    }

    @Test
    fun `Given a valid note THEN the screen OnLoadNoteSuccess must be emitted`() {
        val screenFlow = mainViewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            mainViewModel.dispatch(MainEvents.LoadSelectedNote(VALID_TEST_NOTE.id))
        }

        screenFlow.onCompletion {
            val screen = screenFlow.replayCache.last()

            Truth.assertThat(screen).isEqualTo(MainScreenStates.OnLoadNoteSuccess)
        }
    }

    @Test
    fun `Given a invalid note THEN the screen OnLoadNoteError must be emitted`() {
        val screenFlow = mainViewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            mainViewModel.dispatch(MainEvents.LoadSelectedNote(INVALID_TEST_NOTE.id))
        }

        screenFlow.onCompletion {
            val screen = screenFlow.replayCache.last()

            Truth.assertThat(screen).isEqualTo(MainScreenStates.OnLoadNoteError)
        }
    }

    @Test
    fun `When save button click with valid note THEN the screen OnSaveSuccess must be emitted`() {
        val screenFlow = mainViewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            mainViewModel.dispatch(MainEvents.ClickSaveButton(VALID_TEST_NOTE))
        }

        screenFlow.onCompletion {
            val screen = screenFlow.replayCache.last()

            Truth.assertThat(screen).isEqualTo(MainScreenStates.OnSaveSuccess)
        }
    }

    @Test
    fun `When save button click with invalid note THEN the screen OnSaveError must be emitted`() {
        val screenFlow = mainViewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            mainViewModel.dispatch(MainEvents.ClickSaveButton(INVALID_TEST_NOTE))
        }

        screenFlow.onCompletion {
            val screen = screenFlow.replayCache.last()

            Truth.assertThat(screen).isEqualTo(MainScreenStates.OnSaveError)
        }
    }

    companion object {
        private val VALID_TEST_NOTE = Note(
            id = 1,
            title = "Test Title 1",
            contentText = "Test Content Text 1",
            noteType = NoteTypes.Primary
        )

        private val INVALID_TEST_NOTE = Note()
    }
}