package com.example.devupnotes.framework

import android.content.Context
import com.example.core.data.Note
import com.example.core.repository.NoteDataSource
import com.example.devupnotes.framework.db.DatabaseService
import com.example.devupnotes.framework.db.NoteEntity

class RoomNoteDataSource(context: Context): NoteDataSource {
    val noteDao = DatabaseService.getInstance(context).noteDao()

    // Save Domain Note to Database NoteEntity
    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    // Get Domain Note from Database NoteEntity
    override suspend fun get(id: Long) = noteDao.getNoteEntity(id)?.toNote()

    // Get All Domain Notes from Database NoteEntities
    override suspend fun getAll(): List<Note> = noteDao.getAllNoteEntities().map{ noteEntity -> noteEntity.toNote()  }

    // Remove Domain Note from Database NoteEntity
    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
}