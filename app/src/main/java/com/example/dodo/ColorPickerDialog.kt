package com.example.dodo

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import kotlinx.android.synthetic.main.dialog_color_picker.*
import kotlinx.android.synthetic.main.fragment_todo_set_data.blue_seekbar
import kotlinx.android.synthetic.main.fragment_todo_set_data.color_preview_button
import kotlinx.android.synthetic.main.fragment_todo_set_data.entry_hex_color_string
import kotlinx.android.synthetic.main.fragment_todo_set_data.green_seekbar
import kotlinx.android.synthetic.main.fragment_todo_set_data.red_seekbar

class ColorPickerDialog(context: Context, startColor: String): Dialog(context){
    var red = 127
    var green = 127
    var blue = 127
    var colorHexString = startColor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_color_picker)

        setSeekBars()
        convertColorToHexString()
        dialog_hex_color_string.setText(colorHexString)
        dialog_color_preview_button.setBackgroundColor(Color.parseColor(colorHexString))

        dialog_leave_button.setOnClickListener {
            cancel()
        }

        dialog_red_seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                red = progress
                convertColorToHexString()
                dialog_color_preview_button.setBackgroundColor(Color.parseColor(colorHexString))
            }
        })
        dialog_green_seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                green = progress
                convertColorToHexString()
                dialog_color_preview_button.setBackgroundColor(Color.parseColor(colorHexString))
            }
        })
        dialog_blue_seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                blue = progress
                convertColorToHexString()
                dialog_color_preview_button.setBackgroundColor(Color.parseColor(colorHexString))
            }
        })

        dialog_hex_color_string.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length == 7){
                    colorHexString = s.toString()
                    setSeekBars()
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

    private fun convertColorToHexString() {
        var redValue = Integer.toHexString(red)
        if(redValue.length==1)  redValue = "0$redValue"
        var greenValue = Integer.toHexString(green)
        if(greenValue.length==1)  greenValue = "0$greenValue"
        var blueValue = Integer.toHexString(blue)
        if(blueValue.length==1)  blueValue = "0$blueValue"

        colorHexString = "#$redValue$greenValue$blueValue".toUpperCase()
        dialog_hex_color_string.setText(colorHexString)
    }

    private fun setSeekBars() {
        dialog_red_seekbar.progress = Integer.parseInt(colorHexString.substring(1..2), 16)
        red = dialog_red_seekbar.progress
        dialog_green_seekbar.progress = Integer.parseInt(colorHexString.substring(3..4), 16)
        green = dialog_green_seekbar.progress
        dialog_blue_seekbar.progress = Integer.parseInt(colorHexString.substring(5..6), 16)
        blue = dialog_blue_seekbar.progress
    }
}