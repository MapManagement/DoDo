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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dodo.*
import com.example.proto.DoDoProto
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
    private lateinit var serverConnector: String
    private lateinit var mainActivity: MainActivity
    var recyclerViewItems: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dbConnector =  (activity as MainActivity).dbConnector
        serverConnector = (activity as MainActivity).serverConnector
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity

        fab_add_entry.setOnClickListener {
            openSetDataView()
        }

        recyclerViewItems = view.recycler_view

        notesList = mutableListOf()
        noteAdapter = CustomRecyclerViewAdapter(requireContext(), notesList!!, this)
        recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewItems!!.adapter = noteAdapter
        loadStoredNotes()
    }

    private fun openSetDataView() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, NoteSetDataFragment(), "NOTE_SET")
            commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadStoredNotes() {
        notesList?.clear()
        val storedTasks: ArrayList<DoDoProto.Note> = dbConnector.getAllNotes(mainActivity.usedProfile!!.pid)
        for(note in storedTasks) {
            notesList?.add(note)
        }
        notesList?.sortBy { !it.isHighlighted }
        recyclerViewItems!!.adapter?.notifyDataSetChanged()
    }
}