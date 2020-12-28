package com.example.dodo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseConnector(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE  $TASK_TABLE_NAME (" +
                "$TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$TASK_TEXT TEXT NOT NULL," +
                "$TASK_DONE INTEGER NOT NULL," +
                "$TASK_DELETED INTEGER NOT NULL" +
                ")")

        db?.execSQL("CREATE TABLE  $NOTES_TABLE_NAME (" +
                "$NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NOTE_TEXT TEXT NOT NULL," +
                "$NOTE_VISIBLE INTEGER NOT NULL," +
                "$NOTE_HIGHLIGHTED INTEGER NOT NULL," +
                "$NOTE_COLOR TEXT NOT NULL," +
                "$NOTE_DELETED INTEGER NOT NULL" +
                ")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertNewTask(text: String) {
        val values = ContentValues()
        values.put(TASK_TEXT, text)
        values.put(TASK_DONE, 0)
        values.put(TASK_DELETED, 0)

        val db = this.writableDatabase
        db.insert(TASK_TABLE_NAME, null, values)
        db.close()
    }

    fun deleteTask(taskID: Int) {
        TODO("deleting task from database")
    }

    fun updateTask(taskID: Int) {
        TODO("updating task in database" +
                "text, done")
    }

    fun insertNewNote(text: String, color: String) {
        val values = ContentValues()
        values.put(NOTE_TEXT, text)
        values.put(NOTE_VISIBLE, 1)
        values.put(NOTE_HIGHLIGHTED, 0)
        values.put(NOTE_COLOR, color)
        values.put(NOTE_DELETED, 0)

        val db = this.writableDatabase
        db.insert(NOTES_TABLE_NAME, null, values)
        db.close()
    }

    fun deleteNote(taskID: Int) {
        TODO("deleting task from database")
    }

    fun updateNote(taskID: Int) {
        TODO("updating task in database:" +
                "text, visibility, highlighting, color")
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "dodo.db"
        const val TASK_TABLE_NAME = "Tasks"
        const val NOTES_TABLE_NAME = "Notes"

        const val TASK_ID = "task_id"
        const val TASK_TEXT = "task_text"
        const val TASK_DONE = "is_done"
        const val TASK_DELETED = "is_deleted"

        const val NOTE_ID = "note_id"
        const val NOTE_TEXT = "note_text"
        const val NOTE_VISIBLE = "is_visible"
        const val NOTE_HIGHLIGHTED = "is_highlighted"
        const val NOTE_COLOR = "note_color"
        const val NOTE_DELETED = "is_deleted"
    }
}