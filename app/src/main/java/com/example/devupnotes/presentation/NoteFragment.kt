package com.example.devupnotes.presentation

import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.core.data.Note
import com.example.devupnotes.R
import com.example.devupnotes.databinding.FragmentNoteBinding
import com.example.devupnotes.framework.NoteViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [NoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoteFragment : Fragment() {

    private lateinit var bind: FragmentNoteBinding

    // private lateinit var viewModel: NoteViewModel // old way of getting VM
    private val viewModel: NoteViewModel by viewModels() // new way

    private var currentNote = Note("","",0L, 0L)
    private var oldNote = Note("","",0L, 0L)
    private var noteId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_note, container, false) // old way with synthetix

        bind = FragmentNoteBinding.inflate(inflater, container, false);
        return bind.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java) // old way of getting VM

        // Get arguments (if any)
        arguments?.let { bundle ->
            noteId = NoteFragmentArgs.fromBundle(bundle).noteId

            if(noteId != 0L) { // is it NOT a new note?
                viewModel.getNote(noteId)
            }
        }

        bind.fabSaveButton.setOnClickListener{
            val title = bind.etTitleView.text.toString()
            val content = bind.etContentView.text.toString()
            currentNote.title = title
            currentNote.content = content

            if( (oldNote != currentNote) && (title != "" || content != "")) {
                val time  = System.currentTimeMillis()
                currentNote.updateTime = time

                if (currentNote.id == 0L) { // is it a new note?
                    currentNote.creationTime = time
                }
                viewModel.saveNote(currentNote)
            } else {
                hideKeyboard()
                Navigation.findNavController(it).popBackStack()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {

        // Do nav only after action happens in DB
        viewModel.noteActionTaken.observe(viewLifecycleOwner) { (isSuccessful, actionMsg) ->
            if (isSuccessful) {
                Toast.makeText(context, actionMsg, Toast.LENGTH_SHORT).show()
                hideKeyboard()
                Navigation.findNavController(bind.root).popBackStack()
            } else {
                Toast.makeText(context, "Error, try again", Toast.LENGTH_SHORT).show()
            }
        }

        // get the current note from the VM
        viewModel.currentNote.observe(viewLifecycleOwner) { noteNullable ->
            noteNullable?.let { note ->
                val title = bind.etTitleView
                val content = bind.etContentView

                currentNote = note
                oldNote = note.copy()
                title.setText(note.title) //, TextView.BufferType.EDITABLE) - needed?
                content.setText(note.content)
            }
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(bind.root.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.delete_note -> {
                if(context!= null && noteId != 0L) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Delete Note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes") { dialogInterface, i ->
                            viewModel.deleteNote(currentNote)
                        }
                        .setNegativeButton("Cancel") { dialogInterface, i ->
                            // do nothing
                        }
                        .create()
                        .show()
                }
            }
        }

        return true
    }
}

































