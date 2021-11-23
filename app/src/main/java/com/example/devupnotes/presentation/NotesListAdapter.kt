package com.example.devupnotes.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.Note
import com.example.devupnotes.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotesListAdapter(var notes: ArrayList<Note>): RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    lateinit var viewBind: ItemNoteBinding

    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
//        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        viewBind = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NoteViewHolder(viewBind)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size

    inner class NoteViewHolder(view: ItemNoteBinding): RecyclerView.ViewHolder(viewBind.root) {
        private val layout = viewBind.noteLayout
        private val noteTitle = viewBind.title
        private val noteContent = viewBind.content
        private val noteDate = viewBind.date

        fun bind(note: Note) {
            noteTitle.text = note.title
            noteContent.text = note.content

            val sdf = SimpleDateFormat("MMM dd, HH:mm:ss", Locale.US)
            val resultDate = Date(note.updateTime)
            "Last Updated: ${sdf.format(resultDate)}".also { noteDate.text = it }
        }
    }
}