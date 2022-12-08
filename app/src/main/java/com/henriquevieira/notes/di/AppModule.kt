package com.henriquevieira.notes.di

import android.content.Context
import androidx.room.Room
import com.henriquevieira.core.router.Router
import com.henriquevieira.notes.data.CustomSharedPreferences
import com.henriquevieira.notes.data.CustomSharedPreferencesHandler
import com.henriquevieira.notes.data.CustomSharedPreferencesKeys
import com.henriquevieira.notes.data.room.AppDatabase
import com.henriquevieira.notes.features.router.RouterHandler
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

    @Singleton
    @Provides
    fun provideRouterHandler(@ApplicationContext appContext: Context): Router {
        return RouterHandler(appContext)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "notes"
        ).build()
    }
}