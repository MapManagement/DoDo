package com.example.dodo

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.sip.SipSession
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_entry_dialog.*
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

                red_seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        val colorValue = convertColorToHexString()
                        item_color_preview.setColorFilter(Color.parseColor(colorValue), PorterDuff.Mode.SRC)
                    }
                })
                green_seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        val colorValue = convertColorToHexString()
                        println(colorValue)
                        item_color_preview.setColorFilter(Color.parseColor(colorValue), PorterDuff.Mode.SRC)
                    }
                })
                blue_seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        val colorValue = convertColorToHexString()
                        item_color_preview.setColorFilter(Color.parseColor(colorValue), PorterDuff.Mode.SRC)
                    }
                })
                TODO("changing color of icon")

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

    private fun convertColorToHexString(): String{
        var redValue = Integer.toHexString(red_seekbar.progress)
        if(redValue.length==1)  redValue = "0$redValue"
        var greenValue = Integer.toHexString(green_seekbar.progress)
        if(greenValue.length==1)  greenValue = "0$greenValue"
        var blueValue = Integer.toHexString(blue_seekbar.progress)
        if(blueValue.length==1)  blueValue = "0$blueValue"
        return "#$redValue$greenValue$blueValue"
    }
}
