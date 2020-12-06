package com.example.dodo

import android.app.Dialog
import android.content.Context
import android.os.Bundle

class CustomEntryDialog(context: Context): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_entry_dialog)
    }
}