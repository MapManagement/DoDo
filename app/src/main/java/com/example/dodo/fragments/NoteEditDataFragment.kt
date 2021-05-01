package com.example.dodo.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dodo.DatabaseConnector
import com.example.dodo.Note
import com.example.dodo.R
import kotlinx.android.synthetic.main.fragment_note_edit_data.*
import kotlinx.android.synthetic.main.fragment_note_set_data.*


class NoteEditDataFragment : Fragment() {

    private lateinit var dbConnector: DatabaseConnector

    private var EditedNote: Note = Note()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        EditedNote.noteID = arguments?.getInt("noteID")!!
        EditedNote.noteTitle = arguments?.getString("noteTitle")!!
        EditedNote.noteText = arguments?.getString("noteText")!!
        EditedNote.isVisible = arguments?.getBoolean("noteVisible")!!
        EditedNote.isHighlighted = arguments?.getBoolean("noteHighlighted")!!
        EditedNote.noteColor = arguments?.getString("noteColor")!!


        return inflater.inflate(R.layout.fragment_note_edit_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        note_edit_title_text.setText(EditedNote.noteTitle)
        note_edit_data_text.setText(EditedNote.noteText)

        note_edit_submit_button.setOnClickListener {
            insertNewNote()
            activity!!.supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, NoteFragment())
                commit()
            }
        }

        note_edit_leave_button.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, NoteFragment())
                commit()
            }
        }

        note_edit_color_button.setOnClickListener {
            //ToDo: alert dialog with color picker
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.dbConnector = DatabaseConnector(context, null)
    }

    private fun insertNewNote() {
        EditedNote.noteText = note_edit_data_text.text.toString().trim()
        EditedNote.noteTitle = note_edit_title_text.text.toString().trim()
        dbConnector.updateNote(EditedNote)
        //ToDo: adding color
    }
}