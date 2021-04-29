package com.example.dodo.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dodo.DatabaseConnector
import com.example.dodo.R
import kotlinx.android.synthetic.main.fragment_note_edit_data.*


class NoteEditDataFragment : Fragment() {

    private lateinit var dbConnector: DatabaseConnector

    var noteID: Int? = null
    var noteTitle: String? = null
    var noteText: String? = null
    var noteVisible: Boolean? = null
    var noteHighlighted: Boolean? = null
    var noteColor: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        noteID = arguments?.getInt("noteID")
        noteTitle = arguments?.getString("noteTitle")
        noteText = arguments?.getString("noteText")
        noteVisible = arguments?.getBoolean("noteVisible")
        noteHighlighted = arguments?.getBoolean("noteHighlighted")
        noteColor = arguments?.getString("noteColor")


        return inflater.inflate(R.layout.fragment_note_edit_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        note_edit_title_text.setText(noteTitle)
        note_edit_data_text.setText(noteText)

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
        dbConnector.insertNewNote(note_edit_data_text.text.toString().trim(), note_edit_title_text.text.toString().trim(),
            "")
        //ToDo: adding color
    }
}