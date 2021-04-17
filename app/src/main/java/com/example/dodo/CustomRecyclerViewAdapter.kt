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
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list_item.view.*
import kotlinx.android.synthetic.main.custom_note_item.view.*

var notesList: MutableList<Note>? = null
lateinit var noteAdapter: CustomRecyclerViewAdapter

class CustomRecyclerViewAdapter(context: Context, notes: MutableList<Note>):
    RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder>() {

    private val itemList: MutableList<Note> = notes
    private val dbConnector: DatabaseConnector = DatabaseConnector(context, null)

    inner class ViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {
        val itemTextView = rowView.findViewById(R.id.card_text) as TextView
        val openArea = rowView.findViewById(R.id.card_view) as CardView
        val highlightingButton = rowView.findViewById(R.id.card_highlight_button) as ImageButton
        val visibilityButton = rowView.findViewById(R.id.card_visibility_button) as ImageButton
        val deleteButton = rowView.findViewById(R.id.card_delete_button) as ImageButton
        val relativeLayout = rowView.findViewById(R.id.card_relative_layout) as RelativeLayout
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
        val fragmentLayout = inflater.inflate(R.layout.fragment_note, parent, false)

        return ViewHolder(fragmentLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: Note = itemList[position]

        holder.itemTextView.text = note.noteText
        //ToDo: isHighlighted and isVisible

        //ToDo: isHighlighted in UI

        holder.openArea.setOnClickListener {
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

        holder.deleteButton.setOnClickListener {
            note.isDeleted = true
            itemList.removeAt(position)
            dbConnector.deleteTask(note.noteID)
            this.notifyDataSetChanged()
        }

        val relativeLayoutBackground: Drawable = holder.relativeLayout.background
        relativeLayoutBackground.setColorFilter(Color.parseColor(note.noteColor), PorterDuff.Mode.SRC)
        holder.relativeLayout.background = relativeLayoutBackground
    }
}