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

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

//    val repository = NoteRepository(RoomNoteDataSource(application)) // now injected via DI

    @Inject
    lateinit var useCases: UseCases  // now injected via DI
//    val useCases = UseCases(
//        AddNote(repository),
//        GetAllNotes(repository),
//        GetNote(repository),
//        RemoveNote(repository)
//    )

    init {
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)

//        println("useCases=${useCases.hashCode()}") // checking for singletons
//        println("useCases=${useCases}")
    }

    val noteActionTaken = MutableLiveData<Pair<Boolean,String>>() //(isSuccessful, actionMsg)
    val currentNote = MutableLiveData<Note?>()

    fun saveNote(note: Note) {
        coroutineScope.launch {
            useCases.addNote(note)
            noteActionTaken.postValue(Pair(true, "Note Saved"))
        }
    }

    fun getNote(id: Long) {
        coroutineScope.launch {
            val note = useCases.getNote(id)
            currentNote.postValue(note)
        }
    }

    fun deleteNote(note: Note) {
        coroutineScope.launch {
            useCases.removeNote(note)
            noteActionTaken.postValue(Pair(true, "Note Deleted"))
        }
    }
}