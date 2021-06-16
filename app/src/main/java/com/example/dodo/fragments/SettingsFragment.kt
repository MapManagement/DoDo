package com.example.dodo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import com.example.dodo.DatabaseConnector
import com.example.dodo.MainActivity
import com.example.dodo.R

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var dbConnector: DatabaseConnector
    private lateinit var serverConnector: String

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        dbConnector =  (activity as MainActivity).dbConnector
        serverConnector = (activity as MainActivity).serverConnector
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
    }
}