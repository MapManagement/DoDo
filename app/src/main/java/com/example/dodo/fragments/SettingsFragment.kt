package com.example.dodo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
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

        findPreference<Preference>("server_addr_pref")?.let { bindPreferenceSummaryToValue(it) }
        findPreference<Preference>("profile_name_pref")?.let { bindPreferenceSummaryToValue(it) }
    }

    private val prefListener = Preference.OnPreferenceChangeListener { preference, newValue ->
        if(preference.key == "server_addr_pref") {
            preference.summary = newValue.toString()
        }
        else if(preference.key == "profile_name_pref") {
            preference.summary = newValue.toString()
        }
        true
    }

    private fun bindPreferenceSummaryToValue(preference: Preference) {
        preference.onPreferenceChangeListener = prefListener
        prefListener.onPreferenceChange(preference,
            PreferenceManager
                .getDefaultSharedPreferences(preference.context)
                .getString(preference.key, ""))
            //ToDo: non-editable values need to be set at start
    }
}