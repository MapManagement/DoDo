package com.example.dodo

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_add_entry.setOnClickListener { addNewToDoitem() }
    }

    private fun addNewToDoitem() {
        val alert = AlertDialog.Builder(this)
        val itemText = EditText(this)
        alert.setTitle("ToDo Item")
        alert.setView(itemText)
        alert.setPositiveButton("Add") { _, _ ->

        }
        alert.show()
    }
}