package com.example.dodo.fragments

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.dodo.ColorPickerDialog
import com.example.dodo.DatabaseConnector
import com.example.dodo.Note
import com.example.dodo.R
import kotlinx.android.synthetic.main.dialog_color_picker.*
import kotlinx.android.synthetic.main.fragment_note_edit_data.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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
        EditedNote.noteEditedDatetime = arguments?.getString("noteDatetime")!!


        return inflater.inflate(R.layout.fragment_note_edit_data, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResources()

        note_edit_save_button.setOnClickListener {
            updateNote()
            activity!!.supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, NoteFragment(), "NOTES")
                commit()
            }
        }

        note_edit_highlighting_button.setOnClickListener {
            if(EditedNote.isHighlighted) {
                note_edit_highlighting_button.setImageResource(R.drawable.ic_star_empty)
            }
            else {
                note_edit_highlighting_button.setImageResource(R.drawable.ic_star_filled)
            }
            EditedNote.isHighlighted = !EditedNote.isHighlighted
        }

        note_edit_visibility_button.setOnClickListener {
            if(EditedNote.isVisible) {
                note_edit_visibility_button.setImageResource(R.drawable.ic_visibility_off)
            }
            else {
                note_edit_visibility_button.setImageResource(R.drawable.ic_visibility_on)
            }
            EditedNote.isVisible = !EditedNote.isVisible
        }

        note_edit_color_button.setOnClickListener {
            val colorPicker = ColorPickerDialog(requireContext(), EditedNote.noteColor)
            colorPicker.show()
            colorPicker.dialog_ok_button.setOnClickListener {
                EditedNote.noteColor = colorPicker.colorHexString
                colorPicker.cancel()
                note_edit_data_constraint_layout.setBackgroundColor(Color.parseColor(EditedNote.noteColor))
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.dbConnector = DatabaseConnector(context, null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateNote() {
        EditedNote.noteText = note_edit_data_text.text.toString().trim()
        EditedNote.noteTitle = note_edit_title_text.text.toString().trim()
        EditedNote.noteEditedDatetime = getCurrentDatetimeString()
        dbConnector.updateNote(EditedNote)
    }

    private fun setResources() {
        note_edit_title_text.setText(EditedNote.noteTitle)
        note_edit_data_text.setText(EditedNote.noteText)
        note_edit_date_time_text.text = EditedNote.noteEditedDatetime
        note_edit_data_constraint_layout.setBackgroundColor(Color.parseColor(EditedNote.noteColor))
        if(!EditedNote.isVisible) {
            note_edit_visibility_button.setImageResource(R.drawable.ic_visibility_off)
        }
        if(!EditedNote.isHighlighted) {
            note_edit_highlighting_button.setImageResource(R.drawable.ic_star_empty)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDatetimeString(): String {
        val currentDatetime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

        return currentDatetime.format(formatter)
    }
}