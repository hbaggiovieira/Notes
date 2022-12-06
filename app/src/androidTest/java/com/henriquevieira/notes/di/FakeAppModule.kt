package com.henriquevieira.notes.di

import com.henriquevieira.notes.data.CustomSharedPreferences
import com.henriquevieira.notes.data.CustomSharedPreferencesKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.every
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
    fun provideCustomSharedPreferences(): CustomSharedPreferences = mockk {

        every {
            getString(CustomSharedPreferencesKeys.SELECTED_COLOR, any())
        } returns ""

        every {
            getString(CustomSharedPreferencesKeys.CONTENT_TEXT, any())
        } returns ""

        every {
            putString(CustomSharedPreferencesKeys.CONTENT_TEXT, TEST_CONTENT_TEXT)
        } returns Unit

        every {
            putString(CustomSharedPreferencesKeys.SELECTED_COLOR, TEST_SELECTED_COLOR)
        } returns Unit
    }

    companion object {
        const val TEST_CONTENT_TEXT = "Test content text"
        const val TEST_SELECTED_COLOR = "Primary"
    }
}