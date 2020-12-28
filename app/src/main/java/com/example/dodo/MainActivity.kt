package com.example.dodo

import android.app.AlertDialog
import android.net.sip.SipSession
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_entry_dialog.view.*

class MainActivity : AppCompatActivity(), ItemListener {

    var taskList = ArrayList<String>()
    lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listViewItems = findViewById(R.id.todosListView)

        fab_add_entry.setOnClickListener { showAlertDialog() }

        toDoTaskList = mutableListOf()
        adapter = CustomListAdapter(this, toDoTaskList!!)
        listViewItems!!.setAdapter(adapter)
        TODO("loading stored tasks from database")
    }

    private fun showAlertDialog() {
        val dialogView = layoutInflater.inflate(R.layout.custom_entry_dialog, null)
        AlertDialog.Builder(this)
            .setTitle("Add New Task")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, _which ->
                addNewToDoItem(dialogView.entry_text.text.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { _dialog, _which ->
                Toast.makeText(this, "Cancelled!", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun addNewToDoItem(itemText: String) {
        val taskItem = ToDoTask()
        taskItem.taskText = itemText
        taskItem.isDone = false
        taskItem.isDeleted = false

        layoutInflater.inflate(R.layout.custom_list_item, null)
        toDoTaskList?.add(taskItem)
        adapter.notifyDataSetChanged()
    }

    override fun onItemStatusChanged(iteObjectId: String, isDone: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onItemDeleted(itemObjectId: String) {
        TODO("Not yet implemented")
    }
}
