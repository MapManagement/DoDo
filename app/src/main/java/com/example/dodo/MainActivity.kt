package com.example.dodo

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_add_entry.setOnClickListener { showAlertDialog() }
    }

    private fun showAlertDialog() {
        val editText = EditText(this)
        val dialogView = layoutInflater.inflate(R.layout.custom_entry_dialog, null)
        AlertDialog.Builder(this)
            .setTitle("Add New Task")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, which ->
                addNewToDoitem()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(this, "Cancelled!", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun addNewToDoitem() {

    }
}