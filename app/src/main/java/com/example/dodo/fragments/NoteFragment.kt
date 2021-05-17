package com.example.dodo.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dodo.*
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.fragment_note.view.*
import kotlinx.android.synthetic.main.fragment_to_do.*
import kotlinx.android.synthetic.main.fragment_to_do.fab_add_entry
import kotlinx.android.synthetic.main.fragment_to_do.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [NoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoteFragment : Fragment() {

    private lateinit var dbConnector: DatabaseConnector
    var recyclerViewItems: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_add_entry.setOnClickListener {
            openSetDataView()
        }

        recyclerViewItems = view.recycler_view

        notesList = mutableListOf()
        noteAdapter = CustomRecyclerViewAdapter(requireContext(), notesList!!)
        recycler_view.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewItems!!.adapter = noteAdapter
        loadStoredNotes()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.dbConnector = DatabaseConnector(context, null)
    }

    private fun openSetDataView() {
        activity!!.supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, NoteSetDataFragment(), "NOTE_SET")
            commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadStoredNotes() {
        adapter.notifyDataSetChanged()
        val storedTasks: ArrayList<Note> = dbConnector.getAllNotes()
        for(note in storedTasks) {
            notesList?.add(note)
        }
    }
}