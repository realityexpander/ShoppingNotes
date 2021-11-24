package com.example.devupnotes.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.core.data.Note
import com.example.core.repository.NoteRepository
import com.example.core.usecase.AddNote
import com.example.core.usecase.GetAllNotes
import com.example.core.usecase.GetNote
import com.example.core.usecase.RemoveNote
import com.example.devupnotes.framework.di.ApplicationModule
import com.example.devupnotes.framework.di.DaggerViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel(application: Application): AndroidViewModel(application)  {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

//  val repository = NoteRepository(RoomNoteDataSource(application)) // Now being injected thru UseCases

    @Inject
    lateinit var useCases: UseCases
//    val useCases = UseCases(
//        AddNote(repository),
//        GetAllNotes(repository),
//        GetNote(repository),
//        RemoveNote(repository)
//    )

    init{
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)

        println("useCases=${useCases.hashCode()}")
        println("useCases=${useCases}")
    }

    val notes = MutableLiveData<List<Note>>()

    fun getNotes() {
        coroutineScope.launch {
            val noteList = useCases.getAllNotes()
            notes.postValue(noteList)
        }
    }
}