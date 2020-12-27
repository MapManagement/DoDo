package com.example.dodo

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

var toDoItemList: MutableList<ToDoTask>? = null
lateinit var adapter: CustomListAdapter
var listViewItems: ListView? = null

class CustomListAdapter(context: Context, tasks: MutableList<ToDoTask>): BaseAdapter() {

    private val itemList: MutableList<ToDoTask> = tasks
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return  itemList.size
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemText: String = itemList[position].taskText
        val isDone: Boolean = itemList[position].isDone
        val isDeleted: Boolean = itemList[position].isDeleted

        val rowView = layoutInflater.inflate(R.layout.custom_list_item, null)

        val itemTextView = rowView.findViewById(R.id.text) as TextView
        val checkBox = rowView.findViewById(R.id.item_checkbox) as CheckBox
        val deleteButton = rowView.findViewById(R.id.item_delete_button) as ImageButton

        itemTextView.text = itemText
        checkBox.isChecked = isDone

        checkBox.setOnClickListener {
            checkBox.isChecked = !checkBox.isChecked
        }

        deleteButton.setOnClickListener {
            itemList.removeAt(position)
        }

        return rowView

        TODO("https://www.appsdeveloperblog.com/todo-list-app-kotlin-firebase/")
    }

    private class ListViewItem(row: View) {
        val text: TextView = row.findViewById(R.id.item_text)
        val isDoneCheckBox: CheckBox = row.findViewById(R.id.item_checkbox)
        val deleteButton: ImageButton = row.findViewById(R.id.item_delete_button)
    }
}