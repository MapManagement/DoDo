package com.example.dodo


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class NoteActivity : AppCompatActivity() {

    private val dbConnector: DatabaseConnector = DatabaseConnector(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_todos -> {
                    val activity: DoDoActivities = DoDoActivities()
                    activity.NoteAc = true
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_notes -> {
                    true
                }
                R.id.nav_settings -> {
                    val activity: DoDoActivities = DoDoActivities()
                    activity.NoteAc = true
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
