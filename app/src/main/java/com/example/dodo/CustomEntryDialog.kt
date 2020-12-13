package com.example.dodo

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.custom_entry_dialog.*

class CustomEntryDialog(context: Context): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_entry_dialog)

        // entry_submit_button.setOnClickListener { getEntryText()}
    }

    public fun getEntryText(): String {
        return entry_text.text.toString()
    }

}