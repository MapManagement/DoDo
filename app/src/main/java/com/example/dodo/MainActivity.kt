package com.example.dodo

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.sip.SipSession
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_entry_dialog.view.*

class MainActivity : AppCompatActivity() {

    private val dbConnector: DatabaseConnector = DatabaseConnector(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listViewItems = findViewById(R.id.todosListView)

        fab_add_entry.setOnClickListener { showAlertDialog() }

        toDoTaskList = mutableListOf()
        adapter = CustomListAdapter(this, toDoTaskList!!)
        listViewItems!!.adapter = adapter
        loadStoredTasks()

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_todos -> {
                    true
                }
                R.id.nav_notes -> {
                    val activity: DoDoActivities = DoDoActivities()
                    activity.MainAc = true
                    val intent = Intent(this, NoteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_settings -> {
                    val activity: DoDoActivities = DoDoActivities()
                    activity.MainAc = true
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
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
        dbConnector.insertNewTask(itemText)
        toDoTaskList?.add(taskItem)
        adapter.notifyDataSetChanged()
    }

    private fun loadStoredTasks() {
        val storedTasks: ArrayList<ToDoTask> = dbConnector.getAllTasks()
        for(task in storedTasks) {
            toDoTaskList?.add(task)
        }
    }

    override fun onBackPressed() {
            finish()
    }
}
