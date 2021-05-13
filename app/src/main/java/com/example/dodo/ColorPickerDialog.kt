package com.example.dodo

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_color_picker.*
import kotlinx.android.synthetic.main.fragment_todo_set_data.*

class ColorPickerDialog(context: Context): Dialog(context){
    var red = 127
    var green = 127
    var blue = 127
    var colorHexString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_color_picker)

        colorHexString = convertColorToHexString()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    private fun convertColorToHexString(): String{
        var redValue = Integer.toHexString(red)
        if(redValue.length==1)  redValue = "0$redValue"
        var greenValue = Integer.toHexString(green)
        if(greenValue.length==1)  greenValue = "0$greenValue"
        var blueValue = Integer.toHexString(blue)
        if(blueValue.length==1)  blueValue = "0$blueValue"

        val colorValue = "#$redValue$greenValue$blueValue".toUpperCase()
        dialog_hex_color_string.setText(colorValue)
        return colorValue
    }
}