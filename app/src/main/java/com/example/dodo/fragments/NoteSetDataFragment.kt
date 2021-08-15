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
import com.example.dodo.DoDoHelper
import com.example.dodo.R
import com.example.proto.DoDoProto
import kotlinx.android.synthetic.main.dialog_color_picker.*
import kotlinx.android.synthetic.main.fragment_note_set_data.*
import java.io.Console
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class NoteSetDataFragment : Fragment() {

    private lateinit var dbConnector: DatabaseConnector
    private var dodoHelper: DoDoHelper = DoDoHelper()
    private var NewNote: DoDoProto.Note.Builder = DoDoProto.Note.newBuilder()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_set_data, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NewNote.title = ""
        NewNote.content = ""
        NewNote.isVisible = true
        NewNote.isHighlighted = false
        NewNote.color = "#DFDFDF"

        note_set_data_constraint_layout.setBackgroundColor(Color.parseColor(NewNote.color))

        note_set_submit_button.setOnClickListener {
            insertNewNote()
            requireActivity().supportFragmentManager.beginTransaction().apply {
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
            val colorPicker = ColorPickerDialog(requireContext(), NewNote.color)
            colorPicker.show()
            colorPicker.dialog_ok_button.setOnClickListener {
                NewNote.color = colorPicker.colorHexString
                colorPicker.cancel()
                note_set_data_constraint_layout.setBackgroundColor(Color.parseColor(NewNote.color))
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.dbConnector = DatabaseConnector(context, null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertNewNote() {
        NewNote.content = note_set_data_text.text.toString().trim()
        NewNote.title = note_set_title_text.text.toString().trim()
        NewNote.creationDate = dodoHelper.getCurrentDatetimeString()
        //ToDo: NewNote.noteEditedDatetime = getCurrentDatetimeString()
        dbConnector.insertNewNote(NewNote.build())
    }

}