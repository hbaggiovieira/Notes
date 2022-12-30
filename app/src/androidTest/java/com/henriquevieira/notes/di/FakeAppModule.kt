package com.henriquevieira.notes.di

import com.henriquevieira.commonsui.textinput.NoteType
import com.henriquevieira.core.router.Router
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.domain.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class FakeAppModule {

    @Singleton
    @Provides
    fun provideNoteRepository(): NoteRepository = mockk {
        coEvery {
            getNoteById(any())
        }.coAnswers {
            flowOf(Note(
                id = 0,
                title = "Test Title 1",
                contentText = "Test Content Text 1",
                noteType = NoteType.Primary
            ))
        }

        coEvery {
            saveNote(any())
        }.coAnswers { Unit }

        coEvery {
            deleteNote(any())
        }.coAnswers { Unit }

        coEvery {
            getNotes()
        }.coAnswers {
            flowOf(
                listOf(
                    Note(
                        id = 0,
                        title = "Test Title 1",
                        contentText = "Test Content Text 1",
                        noteType = NoteType.Primary
                    ),
                    Note(
                        id = 1,
                        title = "Test Title 2",
                        contentText = "Test Content Text 2",
                        noteType = NoteType.Red
                    ),
                )
            )
        }
    }


    @Singleton
    @Provides
    fun provideRouter(): Router = mockk {
        coEvery { navigate(any(), any(), any()) }.coAnswers { Unit }
    }
}