package com.example.devupnotes.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.devupnotes.R
import com.example.devupnotes.databinding.FragmentListBinding
import com.example.devupnotes.databinding.FragmentNoteBinding


/**
 * A simple [Fragment] subclass.
 * Use the [NoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoteFragment : Fragment() {

    private lateinit var bind: FragmentNoteBinding

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

        bind.fabSaveButton.setOnClickListener{ Navigation.findNavController(it).popBackStack() }
    }
}