package com.example.dodo


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dodo.fragments.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val dbConnector: DatabaseConnector = DatabaseConnector(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toDoFragment = ToDoFragment()
        val noteFragment = NoteFragment()
        val settingsFragment = SettingsFragment()

        setFragment(toDoFragment, "TODOS")
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_todos -> setFragment(toDoFragment, "TODOS")
                R.id.nav_notes -> setFragment(noteFragment, "NOTES")
                R.id.nav_settings -> setFragment(settingsFragment, "SETTINGS")
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment, tag)
            commit()
        }
    }

    override fun onBackPressed() {
        val lastFragment = supportFragmentManager.fragments.last()

        if(lastFragment is ToDoEditDataFragment || lastFragment is ToDoSetDataFragment){
            setFragment(ToDoFragment(), "TODOS")
        }
        else if(lastFragment is NoteEditDataFragment || lastFragment is NoteSetDataFragment) {
            setFragment(NoteFragment(), "NOTES")
        }
        else if(lastFragment is NoteFragment || lastFragment is ToDoFragment || lastFragment is SettingsFragment) {
            finish()
        }
    }
}
