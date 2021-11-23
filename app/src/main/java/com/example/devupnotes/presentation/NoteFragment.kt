package com.example.devupnotes.presentation

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.core.data.Note
import com.example.devupnotes.databinding.FragmentNoteBinding
import com.example.devupnotes.framework.NoteViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [NoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoteFragment : Fragment() {

    private lateinit var bind: FragmentNoteBinding

    // private lateinit var viewModel: NoteViewModel // old way
    private val viewModel: NoteViewModel by viewModels() // new way

    private var currentNote = Note("","",0L, 0L)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_note, container, false)

        bind = FragmentNoteBinding.inflate(inflater, container, false);
        return bind.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java) // old way

        bind.fabSaveButton.setOnClickListener{
            val title = bind.etTitleView.text.toString()
            val content = bind.etContentView.text.toString()
            if( title != "" || content != "") {
                val time  = System.currentTimeMillis()
                currentNote.title = title
                currentNote.content = content
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
        // only return after saved in DB
        viewModel.noteWasSaved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                Toast.makeText(context, "Note Saved", Toast.LENGTH_SHORT).show()
                hideKeyboard()
                Navigation.findNavController(bind.root).popBackStack()
            } else {
                Toast.makeText(context, "Error, try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(bind.root.windowToken, 0)
    }
}