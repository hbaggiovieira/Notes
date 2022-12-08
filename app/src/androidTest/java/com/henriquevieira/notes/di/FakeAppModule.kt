package com.henriquevieira.notes.di

import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.core.router.Router
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.data.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class FakeAppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(): AppDatabase = mockk {
        coEvery {
            noteDao().getById(any())
        }.coAnswers {
            Note(
                id = 0,
                title = "Test Title 1",
                contentText = "Test Content Text 1",
                noteType = NoteTypes.Primary
            )
        }

        coEvery {
            noteDao().saveNote(any())
        }.coAnswers { Unit }

        coEvery {
            noteDao().delete(any())
        }.coAnswers { Unit }

        coEvery {
            noteDao().getAll()
        }.coAnswers {
            listOf(
                Note(
                    id = 0,
                    title = "Test Title 1",
                    contentText = "Test Content Text 1",
                    noteType = NoteTypes.Primary
                ),
                Note(
                    id = 1,
                    title = "Test Title 2",
                    contentText = "Test Content Text 2",
                    noteType = NoteTypes.Red
                ),
            )
        }
    }

    @Singleton
    @Provides
    fun provideRouter(): Router = mockk {
        coEvery { navigate(any(), any(), any()) }.coAnswers { Unit }
    }

    companion object {
        const val TEST_CONTENT_TEXT = "Test content text"
        const val TEST_SELECTED_COLOR = "Primary"
    }
}