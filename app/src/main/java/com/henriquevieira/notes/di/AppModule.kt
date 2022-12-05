package com.henriquevieira.notes.di

import android.content.Context
import com.henriquevieira.notes.data.CustomSharedPreferences
import com.henriquevieira.notes.data.CustomSharedPreferencesHandler
import com.henriquevieira.notes.data.CustomSharedPreferencesKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideCustomSharedPreferencesHandler(@ApplicationContext appContext: Context): CustomSharedPreferences {
        val sharedPreferences = appContext.getSharedPreferences(CustomSharedPreferencesKeys.PREFERENCES_NAME, Context.MODE_PRIVATE)
        return CustomSharedPreferencesHandler(sharedPreferences)
    }
}