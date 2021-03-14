package com.example.dodo


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val dbConnector: DatabaseConnector = DatabaseConnector(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listViewItems = findViewById(R.id.todosListView)

        toDoTaskList = mutableListOf()
        adapter = CustomListAdapter(this, toDoTaskList!!)
        listViewItems!!.adapter = adapter
        loadStoredTasks()

        /*bottom_navigation.setOnNavigationItemSelectedListener { // ToDo adding working bottom navigation
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
        }*/
    }

    private fun openSetDataView() {
        val intent = Intent(this, SetDataActivity::class.java)
        startActivity(intent)
    }

    private fun loadStoredTasks() {
        adapter.notifyDataSetChanged()
        val storedTasks: ArrayList<ToDoTask> = dbConnector.getAllTasks()
        for(task in storedTasks) {
            toDoTaskList?.add(task)
        }
    }

    override fun onBackPressed() {
            finish()
    }
}
