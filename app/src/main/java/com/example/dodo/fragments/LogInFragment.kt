package com.example.dodo.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.dodo.DatabaseConnector
import com.example.dodo.DoDoHelper
import com.example.dodo.MainActivity
import com.example.dodo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_log_in.*
import java.math.BigInteger
import java.security.MessageDigest

class LogInFragment: Fragment() {
    private lateinit var dbConnector: DatabaseConnector
    private var dodoHelper: DoDoHelper = DoDoHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetEdiTextColor()

        dialog_log_login_button.setOnClickListener {
            if(checkInput()) {

                val profile = dbConnector.checkProfileLogin(dialog_log_name.text.toString(), hashPassword(dialog_log_pass.text.toString()))
                if(profile != null) {
                    val mainActivity = activity as MainActivity
                    mainActivity.usedProfile = profile
                    startDoDo()
                }
                else {
                    Toast.makeText(requireContext(),
                        "Your username or password is incorrect.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialog_log_signup_button.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, SignUpFragment(), "SIGNUP")
                commit()
            }
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

    private fun startDoDo() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, ToDoFragment(), tag)
            commit()
        }
        val parentActivity = requireActivity()
        parentActivity.bottom_navigation.visibility = View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun checkInput(): Boolean {
        val editedName = dialog_log_name.text.trim()
        val validName = editedName.isNotBlank()
        val validPassword = hashPassword(dialog_log_pass.text.toString()).isNotBlank()
        resetEdiTextColor()
        val red = ColorStateList.valueOf(resources.getColor(R.color.red))

        if(!validName) {
            Toast.makeText(requireContext(), "Entered profile name is not valid!", Toast.LENGTH_SHORT).show()
            dialog_log_name.backgroundTintList = red
        }
        else if(!validPassword) {
            Toast.makeText(requireContext(), "There is no password given!", Toast.LENGTH_SHORT).show()
            dialog_log_pass.backgroundTintList = red
        }

        return  validName && validPassword
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun resetEdiTextColor() {
        val black = ColorStateList.valueOf(resources.getColor(R.color.black))
        dialog_log_name.backgroundTintList = black
        dialog_log_pass.backgroundTintList = black
    }
}