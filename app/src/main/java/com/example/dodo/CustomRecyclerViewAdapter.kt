package com.example.dodo

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.custom_list_item.view.*

var notesList: MutableList<Note>? = null
lateinit var noteAdapter: CustomRecyclerViewAdapter

class CustomRecyclerViewAdapter(context: Context, notes: MutableList<Note>): BaseAdapter() {

    private val itemList: MutableList<Note> = notes
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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val note = Note()
        note.noteID = itemList[position].noteID
        note.noteText = itemList[position].noteText
        note.isHighlighted = itemList[position].isHighlighted
        note.isDeleted = itemList[position].isDeleted
        note.isVisible = itemList[position].isVisible
        note.noteColor = itemList[position].noteColor

        val rowView = layoutInflater.inflate(R.layout.custom_note_item, null)

        val itemTextView = rowView.findViewById(R.id.card_text) as TextView
        val openArea = rowView.findViewById(R.id.card_view) as CardView
        //ToDo: adding highlighting button
        //ToDo: adding visibility button
        val deleteButton = rowView.findViewById(R.id.card_delete_button) as ImageButton

        itemTextView.text = note.noteText
        //ToDo: isHighlighted and isVisible

        //ToDo: isHighlighted in UI

        openArea.setOnClickListener {
            //ToDo: opening new fragment
        }
        /*editButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("noteColor", note.noteColor)
            bundle.putString("noteText", note.noteColor)
            bundle.putInt("noteID", note.noteID)

            val activity: AppCompatActivity = convertView!!.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction().apply {
                val editDataFragment = EditDataFragment()
                editDataFragment.arguments = bundle
                replace(R.id.fl_wrapper, editDataFragment)
                commit()
            }
        }*/

        deleteButton.setOnClickListener {
            note.isDeleted = true
            itemList.removeAt(position)
            dbConnector.deleteTask(note.noteID)
            this.notifyDataSetChanged()
        }

        val textSectionBackground: Drawable = rowView.text_section.background
        textSectionBackground.setColorFilter(Color.parseColor(note.noteColor), PorterDuff.Mode.SRC)
        rowView.background = textSectionBackground

//        val textColor = calcTextColor(task.taskColor)
//        println(textColor)
//        val text = rowView.text_section.item_text
//        text.setTextColor(textColor)

        return rowView
    }


}