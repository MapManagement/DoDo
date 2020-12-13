package com.example.dodo

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_entry_dialog.*
import kotlinx.android.synthetic.main.custom_entry_dialog.view.*

class MainActivity : AppCompatActivity() {

    var taskList = ArrayList<String>()
    lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView: ListView = findViewById(R.id.todosListView)

        arrayAdapter = ArrayAdapter(this, R.layout.listview_item, taskList)
        listView.adapter = arrayAdapter

        fab_add_entry.setOnClickListener { showAlertDialog() }
    }

    private fun showAlertDialog() {
        val editText = EditText(this)
        val dialogView = layoutInflater.inflate(R.layout.custom_entry_dialog, null)
        AlertDialog.Builder(this)
            .setTitle("Add New Task")
            .setView(dialogView)
            .setPositiveButton("Add") { _dialog, _which ->
                addNewToDoItem(dialogView.entry_text.text.toString())
            }
            .setNegativeButton("Cancel") { _dialog, _which ->
                Toast.makeText(this, "Cancelled!", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun addNewToDoItem(itemText: String) {
        taskList.add(itemText)
        arrayAdapter.notifyDataSetChanged()
        todosListView.adapter = arrayAdapter
    }
}