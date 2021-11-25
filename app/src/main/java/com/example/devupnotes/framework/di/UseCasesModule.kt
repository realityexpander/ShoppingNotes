package com.example.devupnotes.framework.di

import com.example.core.repository.NoteRepository
import com.example.core.usecase.*
import com.example.devupnotes.framework.UseCases
import dagger.Module
import dagger.Provides
import javax.inject.Scope
import javax.inject.Singleton

@Module
class UseCasesModule {

    @Provides
    fun getUseCases(repository: NoteRepository) = UseCases(  // instantiates all the objects here
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository),
        GetWordCount()
    )
}