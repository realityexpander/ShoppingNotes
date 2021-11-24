package com.example.devupnotes.framework.di

import android.app.Application
import com.example.core.repository.NoteRepository
import com.example.devupnotes.framework.RoomNoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(app: Application) = NoteRepository(RoomNoteDataSource(app))

}