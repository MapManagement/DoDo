package com.example.dodo

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dodo.fragments.ToDoEditDataFragment
import kotlinx.android.synthetic.main.custom_list_item.view.*

var toDoTaskList: MutableList<ToDoTask>? = null
lateinit var adapter: CustomListAdapter

class CustomListAdapter(context: Context, tasks: MutableList<ToDoTask>) : BaseAdapter() {

    private val itemList: MutableList<ToDoTask> = tasks
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val dbConnector: DatabaseConnector = DatabaseConnector(context, null)

    override fun getCount(): Int {
        return itemList.size
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
            val bundle = Bundle()
            bundle.putString("taskColor", task.taskColor)
            bundle.putString("taskText", task.taskText)
            bundle.putInt("taskID", task.taskID)
            val activity: AppCompatActivity = convertView!!.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction().apply {
                val editDataFragment = ToDoEditDataFragment()
                editDataFragment.arguments = bundle
                replace(R.id.fl_wrapper, editDataFragment, "TODO_EDIT")
                commit()
            }
        }

        deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(convertView!!.context)
            builder.setTitle("Confirm Action")
            builder.setMessage("Do you want to delete this ToDo?")
            builder.setPositiveButton("Delete") { dialogInterface: DialogInterface, i: Int ->
                itemList.removeAt(position)
                dbConnector.deleteTask(task.taskID)
                this.notifyDataSetChanged()
                Toast.makeText(convertView.context, "Deleted ToDo", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(convertView.context, "Cancelled", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }

        val textSectionBackground: Drawable = rowView.text_section.background
        textSectionBackground.setColorFilter(Color.parseColor(task.taskColor), PorterDuff.Mode.SRC)
        rowView.background = textSectionBackground

        return rowView
    }
}