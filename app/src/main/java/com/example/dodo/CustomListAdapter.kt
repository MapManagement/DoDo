package com.example.dodo

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.custom_entry_dialog.view.*
import kotlinx.android.synthetic.main.custom_list_item.view.*

var toDoTaskList: MutableList<ToDoTask>? = null
lateinit var adapter: CustomListAdapter
var listViewItems: ListView? = null

class CustomListAdapter(context: Context, tasks: MutableList<ToDoTask>): BaseAdapter() {

    private val itemList: MutableList<ToDoTask> = tasks
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val dbConnector: DatabaseConnector = DatabaseConnector(context, null)

    override fun getCount(): Int {
        return  itemList.size
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val task = ToDoTask()
        task.taskID = itemList[position].taskID
        task.taskText = itemList[position].taskText
        task.isDone = itemList[position].isDone
        task.isDeleted = itemList[position].isDeleted
        task.taskColor = itemList[position].taskColor

        val rowView = layoutInflater.inflate(R.layout.custom_list_item, null)

        val itemTextView = rowView.findViewById(R.id.item_text) as TextView
        val checkBox = rowView.findViewById(R.id.item_checkbox) as CheckBox
        val editButton = rowView.findViewById(R.id.item_edit_button) as ImageButton
        val deleteButton = rowView.findViewById(R.id.item_delete_button) as ImageButton

        itemTextView.text = task.taskText
        checkBox.isChecked = task.isDone

        checkBox.setOnClickListener {
            task.isDone = checkBox.isChecked
            dbConnector.updateTask(task.taskID, task.taskText, task.isDone, "#F0F0F0")
        }

        editButton.setOnClickListener {
            val intent = Intent(parent.context, EditDataActivity::class.java)
            intent.putExtra("taskColor", task.taskColor)
            intent.putExtra("taskText", task.taskText)
            intent.putExtra("taskID", task.taskID)
            parent.context.startActivity(intent)
        }

        deleteButton.setOnClickListener {
            task.isDeleted = true
            itemList.removeAt(position)
            dbConnector.deleteTask(task.taskID)
            this.notifyDataSetChanged()
        }

        val drawableBackground: Drawable = rowView.text_section.background
        drawableBackground.setColorFilter(Color.parseColor(task.taskColor), PorterDuff.Mode.SRC)
        rowView.background = drawableBackground


        return rowView

        TODO("https://www.appsdeveloperblog.com/todo-list-app-kotlin-firebase/")
    }
}