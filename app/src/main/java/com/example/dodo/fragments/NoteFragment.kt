package com.example.dodo.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.dodo.*
import kotlinx.android.synthetic.main.fragment_to_do.*
import kotlinx.android.synthetic.main.fragment_to_do.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [NoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoteFragment : Fragment() {

    private lateinit var dbConnector: DatabaseConnector
    var listViewItems: ListView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_add_entry.setOnClickListener {
            openSetDataView()
        }

        listViewItems = view.todosListView

        notesList = mutableListOf()
        noteAdapter = CustomRecyclerViewAdapter(requireContext(), notesList!!)
        listViewItems!!.adapter = noteAdapter
        loadStoredNotes()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.dbConnector = DatabaseConnector(context, null)
    }

    private fun openSetDataView() {
        activity!!.supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, ToDoSetDataFragment())
            commit()
        }
    }

    private fun loadStoredNotes() {
        adapter.notifyDataSetChanged()
        val storedTasks: ArrayList<Note> = dbConnector.getAllNotes()
        for(note in storedTasks) {
            notesList?.add(note)
        }
    }
}