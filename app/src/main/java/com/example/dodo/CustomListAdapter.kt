package com.example.dodo

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class CustomListAdapter(context: Context, texts: Array<String>): BaseAdapter() {

    private val itemList: Array<String> = texts
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

        val itemText: String = itemList[position]
        val isDOne: Boolean = false

        val rowView = layoutInflater.inflate(R.layout.custom_list_item, null)

        val itemTextView = rowView.findViewById(R.id.text) as TextView
        val checkBox = rowView.findViewById(R.id.item_checkbox) as CheckBox
        val deleteButton = rowView.findViewById(R.id.item_delete_button) as ImageButton

        itemTextView.text = itemList[position]
        checkBox.isChecked = false

        return rowView

        TODO("https://www.appsdeveloperblog.com/todo-list-app-kotlin-firebase/")
    }
}