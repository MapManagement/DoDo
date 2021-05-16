package com.example.dodo

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dodo.fragments.NoteEditDataFragment

var notesList: MutableList<Note>? = null
lateinit var noteAdapter: CustomRecyclerViewAdapter

//ToDo: RecyclerView does not use empty space dynamically
class CustomRecyclerViewAdapter(context: Context, notes: MutableList<Note>) :
    RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder>() {

    private val itemList: MutableList<Note> = notes
    private val dbConnector: DatabaseConnector = DatabaseConnector(context, null)

    inner class ViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {
        val viewContext = rowView.context
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

        //ToDo: beautify if-else
        if (note.noteTitle.isBlank()) {
            if (note.noteText.length > 253) {
                val text = "${note.noteText.subSequence(0, 252)}..."
                holder.itemTextView.text = text
            } else {
                holder.itemTextView.text = note.noteText
            }
        } else {
            holder.itemTextView.text = note.noteTitle
        }
        val noteBackground: LinearLayout = holder.linearLayout
        if (note.noteColor == "") {
            noteBackground.setBackgroundColor(Color.parseColor("#7c827f"))
        } else {
            noteBackground.setBackgroundColor(Color.parseColor(note.noteColor))
        }
        if (note.isHighlighted) holder.highlightingButton.setImageResource(R.drawable.ic_star_filled)
        if (!note.isVisible) holder.visibilityButton.setImageResource(R.drawable.ic_visibility_off)

        holder.visibilityButton.setOnClickListener {
            if (note.isVisible) {
                holder.visibilityButton.setImageResource(R.drawable.ic_visibility_off)
                note.isVisible = false
                dbConnector.updateNote(note)
            }
        }

        holder.highlightingButton.setOnClickListener { //ToDo: sort by highlighting
            if (note.isHighlighted) {
                holder.highlightingButton.setImageResource(R.drawable.ic_star_empty)
                note.isHighlighted = false
                dbConnector.updateNote(note)
            } else {
                holder.highlightingButton.setImageResource(R.drawable.ic_star_filled)
                note.isHighlighted = true
                dbConnector.updateNote(note)
            }
        }

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
                replace(R.id.fl_wrapper, editDataFragment, "NOTE_EDIT")
                commit()
            }
        }

        holder.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(holder.viewContext)
            builder.setTitle("Confirm Action")
            builder.setMessage("Do you want to delete this note?")
            builder.setPositiveButton("Delete") { dialogInterface: DialogInterface, i: Int ->
                itemList.removeAt(position)
                dbConnector.deleteNote(note.noteID)
                this.notifyDataSetChanged()
                Toast.makeText(holder.viewContext, "Deleted Note", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(holder.viewContext, "Cancelled", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }
    }
}