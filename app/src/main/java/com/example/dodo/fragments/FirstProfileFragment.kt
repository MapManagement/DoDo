package com.example.dodo.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dodo.DatabaseConnector
import com.example.dodo.R
import kotlinx.android.synthetic.main.fragment_first_profile.*
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.system.exitProcess

class FirstProfileFragment : Fragment()  {
    private lateinit var dbConnector: DatabaseConnector

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_prof_submit_button.setOnClickListener {
            storeNewProfile()
            startDoDo()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.dbConnector = DatabaseConnector(context, null)
    }

    private fun hashPassword(text: String): String {
        val md = MessageDigest.getInstance("SHA-384")
        val digest = md.digest(text.toByteArray())
        val bigInt = BigInteger(1, digest)
        return bigInt.toString()
    }

    private fun storeNewProfile() {
        val hashedPass = hashPassword(dialog_prof_pass.text.toString())
        val hashedRepPass = hashPassword(dialog_prof_rep_pass.text.toString())
        if (hashedPass == hashedRepPass) {
            //new profile needs to be stored on local database
        }
        //throw error
    }

    private fun startDoDo() {

    }

}