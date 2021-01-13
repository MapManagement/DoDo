package com.example.dodo


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class SettingsActivity : AppCompatActivity() {

    private val dbConnector: DatabaseConnector = DatabaseConnector(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_todos -> {
                    val activity: DoDoActivities = DoDoActivities()
                    activity.SettingsAc = true
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_notes -> {
                    val activity: DoDoActivities = DoDoActivities()
                    activity.SettingsAc = true
                    val intent = Intent(this, NoteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_settings -> {
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
