package com.example.dodo.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.dodo.DatabaseConnector
import com.example.dodo.R
import kotlinx.android.synthetic.main.custom_entry_dialog.*
import kotlinx.android.synthetic.main.custom_entry_dialog.blue_seekbar
import kotlinx.android.synthetic.main.custom_entry_dialog.color_preview_button
import kotlinx.android.synthetic.main.custom_entry_dialog.green_seekbar
import kotlinx.android.synthetic.main.custom_entry_dialog.red_seekbar
import kotlinx.android.synthetic.main.fragment_note_set_data.*
import kotlinx.android.synthetic.main.fragment_todo_set_data.*


class NoteSetDataFragment : Fragment() {

    private lateinit var dbConnector: DatabaseConnector

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_set_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ToDo: adding view stuff

        note_submit_button.setOnClickListener {
            insertNewNote()
            activity!!.supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, NoteFragment())
                commit()
            }
        }

        note_leave_button.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, NoteFragment())
                commit()
            }
        }

        note_color_button.setOnClickListener {
            //ToDo: alert dialog with color picker
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.dbConnector = DatabaseConnector(context, null)
    }

    private fun insertNewNote() {
        dbConnector.insertNewNote(note_data_text.text.toString(), note_title_text.text.toString(), "")
        //ToDo: adding color
    }
}