package com.example.dodo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import kotlinx.android.synthetic.main.activity_set_data.*
import kotlinx.android.synthetic.main.custom_entry_dialog.*
import kotlinx.android.synthetic.main.custom_entry_dialog.blue_seekbar
import kotlinx.android.synthetic.main.custom_entry_dialog.color_preview_button
import kotlinx.android.synthetic.main.custom_entry_dialog.green_seekbar
import kotlinx.android.synthetic.main.custom_entry_dialog.red_seekbar

class SetDataActivity: AppCompatActivity() {

    private val dbConnector: DatabaseConnector = DatabaseConnector(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_data)

        red_seekbar.progress = red_seekbar.max / 2
        green_seekbar.progress = green_seekbar.max / 2
        blue_seekbar.progress = blue_seekbar.max / 2
        val onStartColorValue = convertColorToHexString()
        entry_hex_color_string.setText(onStartColorValue)
        color_preview_button.setBackgroundColor(Color.parseColor(onStartColorValue))

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
            insertNewTask()
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }

        entry_leave_button.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun convertColorToHexString(): String{
        var redValue = Integer.toHexString(red_seekbar.progress)
        if(redValue.length==1)  redValue = "0$redValue"
        var greenValue = Integer.toHexString(green_seekbar.progress)
        if(greenValue.length==1)  greenValue = "0$greenValue"
        var blueValue = Integer.toHexString(blue_seekbar.progress)
        if(blueValue.length==1)  blueValue = "0$blueValue"
        return "#$redValue$greenValue$blueValue"
    }

    private fun insertNewTask() {
        dbConnector.insertNewTask(entry_task_text.text.toString(), convertColorToHexString())

    }
}