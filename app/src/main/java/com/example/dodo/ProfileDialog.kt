package com.example.dodo

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.profile_dialog.*
import java.math.BigInteger
import java.security.MessageDigest

class ProfileDialog(context: Context): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_dialog)

        dialog_prof_submit_button.setOnClickListener {
            storeNewProfile()
            startDoDo()
        }

        dialog_prof_leave_button.setOnClickListener {

        }

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