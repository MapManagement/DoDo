package com.example.dodo

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

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

        val itemID: Int = itemList[position].taskID
        var itemText: String = itemList[position].taskText
        var isDone: Boolean = itemList[position].isDone
        var isDeleted: Boolean = itemList[position].isDeleted

        val rowView = layoutInflater.inflate(R.layout.custom_list_item, null)

        val itemTextView = rowView.findViewById(R.id.item_text) as TextView
        val checkBox = rowView.findViewById(R.id.item_checkbox) as CheckBox
        val editButton = rowView.findViewById(R.id.item_edit_button) as ImageButton
        val deleteButton = rowView.findViewById(R.id.item_delete_button) as ImageButton

        itemTextView.text = itemText
        checkBox.isChecked = isDone

        checkBox.setOnClickListener {
            isDone = checkBox.isChecked
            dbConnector.updateTask(itemID, itemText, isDone)
        }

        editButton.setOnClickListener {
            TODO("showing dialog to edit task text")
        }

        deleteButton.setOnClickListener {
            isDeleted = true
            itemList.removeAt(position)
            dbConnector.deleteTask(itemID)
            this.notifyDataSetChanged()
        }

        return rowView

        TODO("https://www.appsdeveloperblog.com/todo-list-app-kotlin-firebase/")
    }
}