package com.example.dodo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.core.view.isVisible
import com.example.dodo.*
import com.example.proto.DoDoProto
import kotlinx.android.synthetic.main.dialog_color_picker.*
import kotlinx.android.synthetic.main.fragment_note_set_data.*
import kotlinx.android.synthetic.main.fragment_to_do.*
import kotlinx.android.synthetic.main.fragment_to_do.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [ToDoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ToDoFragment() : Fragment() {

    private lateinit var dbConnector: DatabaseConnector
    private lateinit var serverConnector: String
    var listViewItems: ListView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dbConnector =  (activity as MainActivity).dbConnector
        serverConnector = (activity as MainActivity).serverConnector
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_add_entry.setOnClickListener {
            openSetDataView()
        }

        fab_upload_todos.setOnClickListener {
            //ToDo: updating server instance
        }

        fab_open_others.setOnClickListener {
            if(fab_add_entry.isVisible)
            {
                fab_add_entry.visibility = View.INVISIBLE
                fab_upload_todos.visibility = View.INVISIBLE
            }
            else
            {
                fab_add_entry.visibility = View.VISIBLE
                fab_upload_todos.visibility = View.VISIBLE
            }
        }

        listViewItems = view.todosListView

        toDoTaskList = mutableListOf()
        adapter = CustomListAdapter(requireContext(), toDoTaskList!!)
        listViewItems!!.adapter = adapter
        loadStoredTasks()
    }


    private fun openSetDataView() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, ToDoSetDataFragment(), "TODO_SET")
            commit()
        }
    }

    private fun loadStoredTasks() {
        adapter.notifyDataSetChanged()
        val storedTasks: ArrayList<DoDoProto.ToDo> = dbConnector.getAllTodos()
        for(task in storedTasks) {
            toDoTaskList?.add(task)
        }
    }
}