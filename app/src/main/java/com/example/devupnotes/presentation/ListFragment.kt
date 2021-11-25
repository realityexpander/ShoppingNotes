package com.example.devupnotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.devupnotes.databinding.FragmentListBinding
import com.example.devupnotes.framework.ListViewModel
import com.example.devupnotes.framework.NoteViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment(), ListAction {

    private val notesListAdapter = NotesListAdapter(arrayListOf(), this)

    private val viewModel: ListViewModel by viewModels()
    private lateinit var bind: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bind = FragmentListBinding.inflate(inflater, container, false);
        return bind.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.fabAddNote.setOnClickListener{ goToNoteDetails() }

        bind.notesListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesListAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner) { notesList ->
            bind.loadingViewProgress.visibility = View.GONE
            bind.notesListView.visibility = View.VISIBLE
            notesListAdapter.updateNotes(notesList.sortedByDescending { note ->
                note.updateTime
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    private fun goToNoteDetails(id: Long = 0L) {
        val action = ListFragmentDirections.actionGoToNote(id) // set NoteFragmentArgs.noteId
        Navigation.findNavController(bind.root).navigate(action)
    }

    override fun onClickListItem(id: Long) {
        goToNoteDetails(id)
    }
}
