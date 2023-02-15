package com.henriquevieira.notes.features.note

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.henriquevieira.commonsui.textinput.NoteType
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.domain.NoteRepository
import com.henriquevieira.notes.domain.NoteUseCase
import com.henriquevieira.notes.features.note.ui.NoteAction
import com.henriquevieira.notes.features.note.ui.NoteResult
import com.henriquevieira.notes.features.note.viewmodel.NoteViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteViewModelTest {

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

    private val noteViewModel = NoteViewModel(
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
            noteViewModel.dispatch(NoteAction.NoteTypeClick(NoteType.Primary))
        }

        Truth.assertThat(noteViewModel.uiState.value.note.noteType).isEqualTo(NoteType.Primary)
    }

    @Test
    fun `When red color selected THEN noteType must be Red`() {
        runBlocking {
            noteViewModel.dispatch(NoteAction.NoteTypeClick(NoteType.Red))
        }

        Truth.assertThat(noteViewModel.uiState.value.note.noteType).isEqualTo(NoteType.Red)
    }

    @Test
    fun `When green color selected THEN noteType must be Green`() {
        runBlocking {
            noteViewModel.dispatch(NoteAction.NoteTypeClick(NoteType.Green))
        }

        Truth.assertThat(noteViewModel.uiState.value.note.noteType).isEqualTo(NoteType.Green)
    }

    @Test
    fun `When yellow color selected THEN noteType must be Yellow`() {
        runBlocking {
            noteViewModel.dispatch(NoteAction.NoteTypeClick(NoteType.Yellow))
        }

        Truth.assertThat(noteViewModel.uiState.value.note.noteType).isEqualTo(NoteType.Yellow)
    }

    @Test
    fun `When blue color selected THEN noteType must be Blue`() {
        runBlocking {
            noteViewModel.dispatch(NoteAction.NoteTypeClick(NoteType.Blue))
        }

        Truth.assertThat(noteViewModel.uiState.value.note.noteType).isEqualTo(NoteType.Blue)
    }

    @Test
    fun `Given a valid note THEN the screen OnLoadNoteSuccess must be emitted`() {
        val screenFlow = noteViewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            noteViewModel.dispatch(NoteAction.LoadSelectedNote(VALID_TEST_NOTE.id))
        }

        val screen = screenFlow.replayCache.last()

        Truth.assertThat(screen).isEqualTo(NoteResult.OnLoadNoteSuccess)
    }

    @Test
    fun `Given a invalid note THEN the screen OnLoadNoteError must be emitted`() {
        val screenFlow = noteViewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            noteViewModel.dispatch(NoteAction.LoadSelectedNote(INVALID_TEST_NOTE.id))
        }

        val screen = screenFlow.replayCache.last()

        Truth.assertThat(screen).isEqualTo(NoteResult.OnLoadNoteError)
    }

    @Test
    fun `When save button click with valid note THEN the screen OnSaveSuccess must be emitted`() {
        val screenFlow = noteViewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            noteViewModel.dispatch(NoteAction.ClickSaveButton(VALID_TEST_NOTE))
        }

        val screen = screenFlow.replayCache.last()

        Truth.assertThat(screen).isEqualTo(NoteResult.OnSaveSuccess)
    }

    @Test
    fun `When save button click with invalid note THEN the screen OnSaveError must be emitted`() {
        val screenFlow = noteViewModel.screen.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.Eagerly,
            replay = 1
        )

        runBlocking {
            noteViewModel.dispatch(NoteAction.ClickSaveButton(INVALID_TEST_NOTE))
        }

        val screen = screenFlow.replayCache.last()

        Truth.assertThat(screen).isEqualTo(NoteResult.OnSaveError)
    }

    companion object {
        private val VALID_TEST_NOTE = Note(
            id = 1,
            title = "Test Title 1",
            contentText = "Test Content Text 1",
            noteType = NoteType.Primary
        )

        private val INVALID_TEST_NOTE = Note()
    }
}