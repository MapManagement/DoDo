package com.example.dodo.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.dodo.ColorPickerDialog
import com.example.dodo.DatabaseConnector
import com.example.dodo.Note
import com.example.dodo.R
import kotlinx.android.synthetic.main.custom_entry_dialog.*
import kotlinx.android.synthetic.main.custom_entry_dialog.blue_seekbar
import kotlinx.android.synthetic.main.custom_entry_dialog.color_preview_button
import kotlinx.android.synthetic.main.custom_entry_dialog.green_seekbar
import kotlinx.android.synthetic.main.custom_entry_dialog.red_seekbar
import kotlinx.android.synthetic.main.dialog_color_picker.*
import kotlinx.android.synthetic.main.fragment_note_set_data.*
import kotlinx.android.synthetic.main.fragment_todo_set_data.*


class NoteSetDataFragment : Fragment() {

    private lateinit var dbConnector: DatabaseConnector
    private var NewNote: Note = Note()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_set_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NewNote.noteTitle = ""
        NewNote.noteText = ""
        NewNote.isVisible = true
        NewNote.isHighlighted = false
        NewNote.noteColor = "#AAAAAA"

        note_set_submit_button.setOnClickListener {
            insertNewNote()
            activity!!.supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, NoteFragment(), "NOTES")
                commit()
            }
        }

        note_set_highlighting_button.setOnClickListener {
            if(NewNote.isHighlighted) {
                note_set_highlighting_button.setImageResource(R.drawable.ic_star_empty)
            }
            else {
                note_set_highlighting_button.setImageResource(R.drawable.ic_star_filled)
            }
            NewNote.isHighlighted = !NewNote.isHighlighted
        }

        note_set_visibility_button.setOnClickListener {
            if(NewNote.isVisible) {
                note_set_visibility_button.setImageResource(R.drawable.ic_visibility_off)
            }
            else {
                note_set_visibility_button.setImageResource(R.drawable.ic_visibility_on)
            }
            NewNote.isVisible = !NewNote.isVisible
        }

        note_set_color_button.setOnClickListener {
            val colorPicker = ColorPickerDialog(requireContext())
            colorPicker.show()
            colorPicker.dialog_ok_button.setOnClickListener {
                NewNote.noteColor = colorPicker.colorHexString
                colorPicker.cancel()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.dbConnector = DatabaseConnector(context, null)
    }

    private fun insertNewNote() {
        NewNote.noteText = note_set_data_text.text.toString().trim()
        NewNote.noteTitle = note_set_title_text.text.toString().trim()
        dbConnector.insertNewNote(NewNote)
    }
}