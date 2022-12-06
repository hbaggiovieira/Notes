package com.henriquevieira.notes.di

import com.henriquevieira.notes.data.CustomSharedPreferences
import com.henriquevieira.notes.data.CustomSharedPreferencesKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.mockk
import javax.inject.Singleton
//
//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [AppModule::class]
//)

@Module
@InstallIn(SingletonComponent::class)
class FakeAppModule {
    @Singleton
    @Provides
    fun provideCustomSharedPreferences(): CustomSharedPreferences = mockk {
        coEvery {
            getString(any(), "")
        }.coAnswers { TEST_CONTENT_TEXT }

        coEvery {
            putString(CustomSharedPreferencesKeys.CONTENT_TEXT, TEST_CONTENT_TEXT)
        }.coAnswers { Unit }
    }

    companion object {
        const val TEST_CONTENT_TEXT = "Test content text"
    }
}