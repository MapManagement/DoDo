package com.example.dodo.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.dodo.*
import kotlinx.android.synthetic.main.dialog_color_picker.*
import kotlinx.android.synthetic.main.fragment_note_set_data.*
import kotlinx.android.synthetic.main.fragment_to_do.*
import kotlinx.android.synthetic.main.fragment_to_do.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [ToDoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ToDoFragment : Fragment() {

    private lateinit var dbConnector: DatabaseConnector
    var listViewItems: ListView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_add_entry.setOnClickListener {
            val colorPicker = ColorPickerDialog(requireContext(), "#F1F1F1")
            colorPicker.show()
            colorPicker.dialog_ok_button.setOnClickListener {

            }
        }

        listViewItems = view.todosListView

        toDoTaskList = mutableListOf()
        adapter = CustomListAdapter(requireContext(), toDoTaskList!!)
        listViewItems!!.adapter = adapter
        loadStoredTasks()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.dbConnector = DatabaseConnector(context, null)
    }

    private fun openSetDataView() {
        activity!!.supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, ToDoSetDataFragment(), "TODO_SET")
            commit()
        }
    }

    private fun loadStoredTasks() {
        adapter.notifyDataSetChanged()
        val storedTasks: ArrayList<ToDoTask> = dbConnector.getAllTasks()
        for(task in storedTasks) {
            toDoTaskList?.add(task)
        }
    }
}