package com.example.dodo

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dodo.fragments.NoteEditDataFragment
import com.example.dodo.fragments.NoteSetDataFragment
import kotlinx.android.synthetic.main.custom_list_item.view.*
import kotlinx.android.synthetic.main.custom_note_item.view.*

var notesList: MutableList<Note>? = null
lateinit var noteAdapter: CustomRecyclerViewAdapter

class CustomRecyclerViewAdapter(context: Context, notes: MutableList<Note>):
    RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder>() {

    private val itemList: MutableList<Note> = notes
    private val dbConnector: DatabaseConnector = DatabaseConnector(context, null)

    inner class ViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {
        val itemTextView: TextView = rowView.findViewById(R.id.note_text)
        val openArea: CardView = rowView.findViewById(R.id.note_card_view)
        val highlightingButton: ImageButton = rowView.findViewById(R.id.note_highlight_button)
        val visibilityButton: ImageButton = rowView.findViewById(R.id.note_visibility_button)
        val deleteButton: ImageButton = rowView.findViewById(R.id.note_delete_button)
        val linearLayout: LinearLayout = rowView.findViewById(R.id.note_linear_layout)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val fragmentLayout = inflater.inflate(R.layout.custom_note_item, parent, false)

        return ViewHolder(fragmentLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: Note = itemList[position]

        holder.itemTextView.text = note.noteTitle
        val noteBackground: LinearLayout = holder.linearLayout
        if(note.noteColor == "") {
            noteBackground.setBackgroundColor(Color.parseColor("#7c827f"))
        }
        else {
            noteBackground.setBackgroundColor(Color.parseColor(note.noteColor))
        }


        //ToDo: isHighlighted and isVisible

        //ToDo: isHighlighted in UI

        holder.openArea.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("noteColor", note.noteColor)
            bundle.putString("noteText", note.noteText)
            bundle.putString("noteTitle", note.noteTitle)
            bundle.putInt("noteID", note.noteID)
            bundle.putBoolean("noteVisible", note.isVisible)
            bundle.putBoolean("noteHighlighted", note.isHighlighted)
            val activity = it.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction().apply {
                val editDataFragment = NoteEditDataFragment()
                editDataFragment.arguments = bundle
                replace(R.id.fl_wrapper, editDataFragment)
                commit()
            }
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

        holder.deleteButton.setOnClickListener {
            note.isDeleted = true
            itemList.removeAt(position)
            dbConnector.deleteNote(note.noteID)
            this.notifyDataSetChanged()
        }

//        val relativeLayoutBackground: Drawable = holder.relativeLayout.background
//        relativeLayoutBackground.setColorFilter(Color.parseColor(note.noteColor), PorterDuff.Mode.SRC)
//        holder.relativeLayout.background = relativeLayoutBackground
    }
}