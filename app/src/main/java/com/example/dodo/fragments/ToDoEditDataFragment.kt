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
import kotlinx.android.synthetic.main.custom_entry_dialog.blue_seekbar
import kotlinx.android.synthetic.main.custom_entry_dialog.color_preview_button
import kotlinx.android.synthetic.main.custom_entry_dialog.green_seekbar
import kotlinx.android.synthetic.main.custom_entry_dialog.red_seekbar
import kotlinx.android.synthetic.main.fragment_todo_edit_data.*


class ToDoEditDataFragment : Fragment() {
    private lateinit var dbConnector: DatabaseConnector

    var taskText: String? = null
    var taskID: Int? = null
    var taskColor: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        taskText = arguments?.getString("taskText")
        taskID = arguments?.getInt("taskID")
        taskColor = arguments?.getString("taskColor")

        return inflater.inflate(R.layout.fragment_todo_edit_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entry_task_text.setText(taskText)

        red_seekbar.progress = red_seekbar.max / 2
        green_seekbar.progress = green_seekbar.max / 2
        blue_seekbar.progress = blue_seekbar.max / 2
        entry_hex_color_string.setText(taskColor)
        setSeekBars(taskColor!!)
        color_preview_button.setBackgroundColor(Color.parseColor(taskColor))

        red_seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val colorValue = convertColorToHexString()
                color_preview_button.setBackgroundColor(Color.parseColor(colorValue))
            }
        })
        green_seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val colorValue = convertColorToHexString()
                color_preview_button.setBackgroundColor(Color.parseColor(colorValue))
            }
        })
        blue_seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val colorValue = convertColorToHexString()
                color_preview_button.setBackgroundColor(Color.parseColor(colorValue))
            }
        })

        entry_submit_button.setOnClickListener {
            if(entry_task_text.text.toString().isNotBlank() && taskID != null) {
                updateTask(taskID!!)
                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fl_wrapper, ToDoFragment(), "TODOS")
                    commit()
                }
            }
        }

        entry_leave_button.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, ToDoFragment(), "TODOS")
                commit()
            }
        }

        entry_hex_color_string.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length == 7){
                    setSeekBars(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                return
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.dbConnector = DatabaseConnector(context, null)
    }

    private fun convertColorToHexString(): String {
        var redValue = Integer.toHexString(red_seekbar.progress)
        if(redValue.length==1)  redValue = "0$redValue"
        var greenValue = Integer.toHexString(green_seekbar.progress)
        if(greenValue.length==1)  greenValue = "0$greenValue"
        var blueValue = Integer.toHexString(blue_seekbar.progress)
        if(blueValue.length==1)  blueValue = "0$blueValue"

        val colorValue = "#$redValue$greenValue$blueValue".toUpperCase()
        entry_hex_color_string.setText(colorValue)
        return colorValue
    }

    private fun setSeekBars(colorString: String) {
        red_seekbar.progress = Integer.parseInt(colorString.substring(1..2), 16)
        green_seekbar.progress = Integer.parseInt(colorString.substring(3..4), 16)
        blue_seekbar.progress = Integer.parseInt(colorString.substring(5..6), 16)
    }

    private fun updateTask(taskID: Int) {
        dbConnector.updateTask(taskID, entry_task_text.text.toString(), false,
            convertColorToHexString())
    }
}