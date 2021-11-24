package com.example.devupnotes.framework.di

import com.example.devupnotes.framework.ListViewModel
import com.example.devupnotes.framework.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class]) // FROM these modules
interface ViewModelComponent {
    fun inject(noteViewModel: NoteViewModel) // inject INTO this class
    fun inject(listViewModel: ListViewModel) // inject INTO this class
}