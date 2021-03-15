package com.example.dodo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dodo.R

/**
 * A simple [Fragment] subclass.
 * Use the [SetDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetDataFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_data, container, false)
    }
}