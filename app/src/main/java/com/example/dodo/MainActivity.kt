package com.example.dodo


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dodo.fragments.NoteFragment
import com.example.dodo.fragments.SettingsFragment
import com.example.dodo.fragments.ToDoFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val dbConnector: DatabaseConnector = DatabaseConnector(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toDoFragment = ToDoFragment()
        val noteFragment = NoteFragment()
        val settingsFragment = SettingsFragment()

        setFragment(toDoFragment)
        bottom_navigation.setOnNavigationItemSelectedListener { // ToDo adding working bottom navigation
            when(it.itemId) {
                R.id.nav_todos -> setFragment(toDoFragment)
                R.id.nav_notes -> setFragment(noteFragment)
                R.id.nav_settings -> setFragment(settingsFragment)
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    }

    override fun onBackPressed() {
            finish()
    }
}
