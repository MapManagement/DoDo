package com.example.dodo


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class NoteActivity(openingActivity: Activity) : AppCompatActivity() {

    private val dbConnector: DatabaseConnector = DatabaseConnector(this, null)
    private val lastActivity: Activity = openingActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_todos -> {
                    val intent = Intent(this, MainActivity(this)::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_notes -> {
                    true
                }
                R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity(this)::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, lastActivity::class.java)
        startActivity(intent)
    }
}