package com.example.dodo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.custom_entry_dialog.view.*

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
            val dialogView = layoutInflater.inflate(R.layout.custom_entry_dialog, null)
            AlertDialog.Builder(parent.context)
                .setTitle("Edit Task")
                .setView(dialogView)
                .setPositiveButton("Edit") { dialog, _which ->
                    itemText = dialogView.entry_text.text.toString()
                    itemTextView.text = itemText

                    dbConnector.updateTask(itemID, itemText, isDone)
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _which ->
                    dialog.dismiss()
                }
                .show()
        }

        deleteButton.setOnClickListener {
            isDeleted = true
            itemList.removeAt(position)
            dbConnector.deleteTask(itemID)
            this.notifyDataSetChanged()
        }

        val drawableBackground: Drawable = rowView.background
        drawableBackground.setColorFilter(Color.parseColor("#9966FF"), PorterDuff.Mode.SRC)
        rowView.background = drawableBackground


        return rowView

        TODO("https://www.appsdeveloperblog.com/todo-list-app-kotlin-firebase/")
    }
}