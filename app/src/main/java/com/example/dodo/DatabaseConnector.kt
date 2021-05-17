package com.example.dodo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DatabaseConnector(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE  $TASK_TABLE_NAME (" +
                "$TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$TASK_TEXT TEXT NOT NULL," +
                "$TASK_DONE INTEGER NOT NULL," +
                "$TASK_COLOR TEXT NOT NULL" +
                ")")

        db?.execSQL("CREATE TABLE  $NOTE_TABLE_NAME (" +
                "$NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NOTE_TITLE TEXT," +
                "$NOTE_TEXT TEXT NOT NULL," +
                "$NOTE_VISIBLE INTEGER NOT NULL," +
                "$NOTE_HIGHLIGHTED INTEGER NOT NULL," +
                "$NOTE_COLOR TEXT NOT NULL," +
                "$NOTE_DATETIME TEXT NOT NULL" +
                ")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun getAllTasks(): ArrayList<ToDoTask> {
        val db = this.readableDatabase
        val taskArray = arrayListOf<ToDoTask>()
        val cursor = db.rawQuery("SELECT * FROM $TASK_TABLE_NAME", null)
        cursor.moveToFirst()
        while(!cursor.isAfterLast) {
            val toDoTask = ToDoTask()
            toDoTask.taskID = cursor.getInt(cursor.getColumnIndex(TASK_ID))
            toDoTask.taskText = cursor.getString(cursor.getColumnIndex(TASK_TEXT))
            toDoTask.isDone = cursor.getInt(cursor.getColumnIndex(TASK_DONE)) == 1
            toDoTask.taskColor = cursor.getString(cursor.getColumnIndex(TASK_COLOR))
            taskArray.add(toDoTask)
            cursor.moveToNext()
        }
        cursor.close()
        return taskArray
    }

    fun insertNewTask(text: String, color: String) {
        val values = ContentValues()
        values.put(TASK_TEXT, text)
        values.put(TASK_COLOR, color)
        values.put(TASK_DONE, 0)

        val db = this.writableDatabase
        db.insert(TASK_TABLE_NAME, null, values)
        db.close()
    }

    fun deleteTask(taskID: Int) {
        val db = this.writableDatabase
        db.delete(TASK_TABLE_NAME, "$TASK_ID = ?", arrayOf(taskID.toString()))
        db.close()
    }

    fun updateTask(taskID: Int, text: String, isDone: Boolean, color: String) {
        val intIsDone = if(isDone) 1 else 0
        val values = ContentValues()
        values.put(TASK_TEXT, text)
        values.put(TASK_COLOR, color)
        values.put(TASK_DONE, intIsDone)

        val db = this.writableDatabase
        db.update(TASK_TABLE_NAME, values, "$TASK_ID = ?", arrayOf(taskID.toString()))
        db.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllNotes(): ArrayList<Note> {
        val db = this.readableDatabase
        val noteArray = arrayListOf<Note>()
        val cursor = db.rawQuery("SELECT * FROM $NOTE_TABLE_NAME", null)
        cursor.moveToFirst()

        while(!cursor.isAfterLast) {
            val note = Note()
            note.noteID = cursor.getInt(cursor.getColumnIndex(NOTE_ID))
            note.noteTitle = cursor.getString(cursor.getColumnIndex(NOTE_TITLE))
            note.noteText = cursor.getString(cursor.getColumnIndex(NOTE_TEXT))
            note.isVisible = cursor.getInt(cursor.getColumnIndex(NOTE_VISIBLE)) == 1
            note.isHighlighted = cursor.getInt(cursor.getColumnIndex(NOTE_HIGHLIGHTED)) == 1
            note.noteColor = cursor.getString(cursor.getColumnIndex(NOTE_COLOR))
            note.noteEditedDatetime = cursor.getString(cursor.getColumnIndex(NOTE_DATETIME))
            noteArray.add(note)
            cursor.moveToNext()
        }
        cursor.close()
        return noteArray
    }

    fun insertNewNote(note: Note) {
        val intIsVisible = if(note.isVisible) 1 else 0
        val intIsHighlighted = if(note.isHighlighted) 1 else 0
        val values = ContentValues()
        values.put(NOTE_TEXT, note.noteText)
        values.put(NOTE_TITLE, note.noteTitle)
        values.put(NOTE_VISIBLE, intIsVisible)
        values.put(NOTE_HIGHLIGHTED, intIsHighlighted)
        values.put(NOTE_COLOR, note.noteColor)
        values.put(NOTE_DATETIME, note.noteEditedDatetime)

        val db = this.writableDatabase
        db.insert(NOTE_TABLE_NAME, null, values)
        db.close()
    }

    fun deleteNote(noteID: Int) {
        val db = this.writableDatabase
        db.delete(NOTE_TABLE_NAME, "$NOTE_ID = ?", arrayOf(noteID.toString()))
        db.close()
    }

    fun updateNote(note: Note) {
        val intIsVisible = if(note.isVisible) 1 else 0
        val intIsHighlighted = if(note.isHighlighted) 1 else 0
        val values = ContentValues()
        values.put(NOTE_TEXT, note.noteText)
        values.put(NOTE_TITLE, note.noteTitle)
        values.put(NOTE_VISIBLE, intIsVisible)
        values.put(NOTE_HIGHLIGHTED, intIsHighlighted)
        values.put(NOTE_COLOR, note.noteColor)
        values.put(NOTE_DATETIME, note.noteEditedDatetime)

        val db = this.writableDatabase
        db.update(NOTE_TABLE_NAME, values, "$NOTE_ID = ?", arrayOf(note.noteID.toString()))
        db.close()
    }

    companion object {
        // ToDo: adding missing fields
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "dodo.db"
        const val TASK_TABLE_NAME = "Tasks"
        const val NOTE_TABLE_NAME = "Notes"

        const val TASK_ID = "id"
        const val TASK_TEXT = "text"
        const val TASK_COLOR = "color"
        const val TASK_DONE = "is_done"

        const val NOTE_ID = "id"
        const val NOTE_TITLE = "title"
        const val NOTE_TEXT = "text"
        const val NOTE_VISIBLE = "is_visible"
        const val NOTE_HIGHLIGHTED = "is_highlighted"
        const val NOTE_COLOR = "color"
        const val NOTE_DATETIME = "edited_datetime"
    }
}