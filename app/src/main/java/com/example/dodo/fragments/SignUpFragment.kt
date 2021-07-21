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
import androidx.preference.PreferenceManager
import com.example.dodo.DatabaseConnector
import com.example.dodo.R
import com.example.proto.DoDoProto
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*


class SignUpFragment : Fragment()  {
    private lateinit var dbConnector: DatabaseConnector

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetEdiTextColor()

        dialog_prof_submit_button.setOnClickListener {
            if(checkInput()) {
                storeNewProfile()
                startDoDo()
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

    private fun storeNewProfile() {
        //ToDo: check if the entered profile name already exists
        val hashedPass = hashPassword(dialog_prof_pass.text.toString())
        val newProfile = DoDoProto.Profile.newBuilder()
        newProfile.name = dialog_prof_name.text.toString()
        newProfile.password = hashedPass
        newProfile.creationDate = getDodoDatetimeNow()

        val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val editor = pref.edit()
        editor.putString("profile_name_pref", dialog_prof_name.text.toString())
        editor.putInt("profile_id_pref", 0) //ToDo: get ID of newly created profile
        editor.putString("profile_creation_date_pref", "") //ToDo: set creation date
        editor.apply()
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
        val editedName = dialog_prof_name.text.trim()
        val validName = editedName.isNotBlank()
        val validPassword = dialog_prof_pass.text.isNotBlank() && dialog_prof_label_rep_pass.text.isNotBlank()
        val samePasswords = hashPassword(dialog_prof_pass.text.toString()) == hashPassword(dialog_prof_rep_pass.text.toString())

        resetEdiTextColor()
        val red = ColorStateList.valueOf(resources.getColor(R.color.red))

        if(!validName) {
            Toast.makeText(requireContext(), "Given profile name is not valid!", Toast.LENGTH_SHORT).show()
            dialog_prof_name.backgroundTintList = red
        }
        else if(!validPassword) {
            Toast.makeText(requireContext(), "There is no password given!", Toast.LENGTH_SHORT).show()
            dialog_prof_pass.backgroundTintList = red
            dialog_prof_rep_pass.backgroundTintList = red
        }
        else if(!samePasswords) {
            Toast.makeText(requireContext(), "The given passswords do not match!", Toast.LENGTH_SHORT).show()
            dialog_prof_pass.backgroundTintList = red
            dialog_prof_rep_pass.backgroundTintList = red
        }

        return  validName && validPassword && samePasswords
    }

    private fun getDodoDatetimeNow(): DoDoProto.DateTime? {
        val datetime = DoDoProto.DateTime.newBuilder()
        val datetimeNow = Calendar.getInstance().time

        datetime.year = datetimeNow.year
        datetime.month = datetimeNow.month
        datetime.day = datetimeNow.day
        datetime.hour = datetimeNow.hours
        datetime.minute = datetimeNow.minutes
        datetime.second = datetimeNow.seconds

        return datetime.build()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun resetEdiTextColor() {
        val black = ColorStateList.valueOf(resources.getColor(R.color.black))
        dialog_prof_name.backgroundTintList = black
        dialog_prof_pass.backgroundTintList = black
        dialog_prof_rep_pass.backgroundTintList = black
    }
}