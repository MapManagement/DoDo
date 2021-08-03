package com.example.dodo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.proto.DoDoProto
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DatabaseConnector(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE  $TODO_TABLE_NAME (" +
                "$TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$TASK_CREATOR_ID INTEGER," +
                "$TASK_TEXT TEXT NOT NULL," +
                "$TASK_DONE INTEGER NOT NULL," +
                "$TASK_COLOR TEXT NOT NULL," +
                "FOREIGN KEY($TASK_CREATOR_ID) REFERENCES $PROFILE_TABLE_NAME($PROFILE_ID)" +
                ")")

        db?.execSQL("CREATE TABLE  $NOTE_TABLE_NAME (" +
                "$NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NOTE_CREATOR_ID INTEGER," +
                "$NOTE_TITLE TEXT," +
                "$NOTE_CONTENT TEXT NOT NULL," +
                "$NOTE_VISIBLE INTEGER NOT NULL," +
                "$NOTE_HIGHLIGHTED INTEGER NOT NULL," +
                "$NOTE_COLOR TEXT NOT NULL," +
                "$NOTE_DATE TEXT NOT NULL," +
                "FOREIGN KEY($NOTE_CREATOR_ID) REFERENCES $PROFILE_TABLE_NAME($PROFILE_ID)" +
                ")")

        db?.execSQL("CREATE TABLE  $PROFILE_TABLE_NAME (" +
                "$PROFILE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$PROFILE_NAME TEXT," +
                "$PROFILE_PASSWORD TEXT," +
                "$PROFILE_DATE TEXT NOT NULL" +
                ")")

        db?.execSQL("CREATE TABLE  $TAG_TABLE_NAME (" +
                "$TAG_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$TAG_NAME TEXT" +
                ")")

        db?.execSQL("CREATE TABLE  $NOTE_TAG_REL_TABLE_NAME (" +
                "$NOTE_TAG_REL_NID INTEGER," +
                "$NOTE_TAG_REL_TAID INTEGER," +
                "FOREIGN KEY($NOTE_TAG_REL_NID) REFERENCES $NOTE_TABLE_NAME($NOTE_ID)" +
                ")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun getAllTodos(): ArrayList<DoDoProto.ToDo> {
        val db = this.readableDatabase
        val todoArray = arrayListOf<DoDoProto.ToDo>()
        val cursor = db.rawQuery("SELECT * FROM $TODO_TABLE_NAME", null)
        cursor.moveToFirst()
        while(!cursor.isAfterLast) {
            val toDoTask = DoDoProto.ToDo.newBuilder()
            toDoTask.tid = cursor.getInt(cursor.getColumnIndex(TASK_ID))
            toDoTask.text = cursor.getString(cursor.getColumnIndex(TASK_TEXT))
            toDoTask.isDone = cursor.getInt(cursor.getColumnIndex(TASK_DONE)) == 1
            toDoTask.color = cursor.getString(cursor.getColumnIndex(TASK_COLOR))
            //ToDo: add creator_id
            todoArray.add(toDoTask.build())
            cursor.moveToNext()
        }
        cursor.close()
        return todoArray
    }

    fun insertNewTask(text: String, color: String) {
        val values = ContentValues()
        values.put(TASK_TEXT, text)
        values.put(TASK_COLOR, color)
        values.put(TASK_DONE, 0)
        //ToDo: insert creator_id

        val db = this.writableDatabase
        db.insert(TODO_TABLE_NAME, null, values)
        db.close()
    }

    fun deleteTask(taskID: Int) {
        val db = this.writableDatabase
        db.delete(TODO_TABLE_NAME, "$TASK_ID = ?", arrayOf(taskID.toString()))
        db.close()
    }

    fun updateTask(taskID: Int, text: String, isDone: Boolean, color: String) {
        val intIsDone = if(isDone) 1 else 0
        val values = ContentValues()
        values.put(TASK_TEXT, text)
        values.put(TASK_COLOR, color)
        values.put(TASK_DONE, intIsDone)

        val db = this.writableDatabase
        db.update(TODO_TABLE_NAME, values, "$TASK_ID = ?", arrayOf(taskID.toString()))
        db.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllNotes(): ArrayList<DoDoProto.Note> {
        val db = this.readableDatabase
        val noteArray = arrayListOf<DoDoProto.Note>()
        val cursor = db.rawQuery("SELECT * FROM $NOTE_TABLE_NAME", null)
        cursor.moveToFirst()

        while(!cursor.isAfterLast) {
            val note = DoDoProto.Note.newBuilder()
            note.nid = cursor.getInt(cursor.getColumnIndex(NOTE_ID))
            note.title = cursor.getString(cursor.getColumnIndex(NOTE_TITLE))
            note.content = cursor.getString(cursor.getColumnIndex(NOTE_CONTENT))
            note.isVisible = cursor.getInt(cursor.getColumnIndex(NOTE_VISIBLE)) == 1
            note.isHighlighted = cursor.getInt(cursor.getColumnIndex(NOTE_HIGHLIGHTED)) == 1
            note.color = cursor.getString(cursor.getColumnIndex(NOTE_COLOR))
            //ToDo: add creation_date
            noteArray.add(note.build())
            cursor.moveToNext()
        }
        cursor.close()
        return noteArray
    }

    fun insertNewNote(note: DoDoProto.Note) {
        val intIsVisible = if(note.isVisible) 1 else 0
        val intIsHighlighted = if(note.isHighlighted) 1 else 0
        val values = ContentValues()
        values.put(NOTE_CONTENT, note.content)
        values.put(NOTE_TITLE, note.title)
        values.put(NOTE_VISIBLE, intIsVisible)
        values.put(NOTE_HIGHLIGHTED, intIsHighlighted)
        values.put(NOTE_COLOR, note.color)
        //ToDo: insert creator_id & creation_date

        val db = this.writableDatabase
        db.insert(NOTE_TABLE_NAME, null, values)
        db.close()
    }

    fun deleteNote(noteID: Int) {
        val db = this.writableDatabase
        db.delete(NOTE_TABLE_NAME, "$NOTE_ID = ?", arrayOf(noteID.toString()))
        db.close()
    }

    fun updateNote(note: DoDoProto.Note) {
        val intIsVisible = if(note.isVisible) 1 else 0
        val intIsHighlighted = if(note.isHighlighted) 1 else 0
        val values = ContentValues()
        values.put(NOTE_CONTENT, note.content)
        values.put(NOTE_TITLE, note.title)
        values.put(NOTE_VISIBLE, intIsVisible)
        values.put(NOTE_HIGHLIGHTED, intIsHighlighted)
        values.put(NOTE_COLOR, note.color)

        val db = this.writableDatabase
        db.update(NOTE_TABLE_NAME, values, "$NOTE_ID = ?", arrayOf(note.nid.toString()))
        db.close()
    }

    fun insertNewProfile(profile: DoDoProto.Profile) {
        val values = ContentValues()
        values.put(PROFILE_NAME, profile.name)
        values.put(PROFILE_PASSWORD, profile.password)
        values.put(PROFILE_DATE, profile.creationDate.toString()) //ToDo: gRPC class to readable datetime

        val db = this.writableDatabase
        db.insert(PROFILE_TABLE_NAME, null, values)
        db.close()
    }

    fun deleteProfile(profileID: Int) {
        val db = this.writableDatabase
        db.delete(PROFILE_TABLE_NAME, "$PROFILE_ID = ?", arrayOf(profileID.toString()))
        db.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateProfile(profile: DoDoProto.Profile): Boolean {
        if(!isProfileNameAvailable(profile.name)) return false

        val values = ContentValues()
        values.put(PROFILE_NAME, profile.name)
        values.put(PROFILE_PASSWORD, profile.password)

        val db = this.writableDatabase
        db.update(PROFILE_TABLE_NAME, values, "$PROFILE_ID = ?", arrayOf(profile.pid.toString()))
        db.close()
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllProfiles(): ArrayList<DoDoProto.Profile> {
        val db = this.readableDatabase
        val profileArray = arrayListOf<DoDoProto.Profile>()
        val cursor = db.rawQuery("SELECT * FROM $PROFILE_TABLE_NAME", null)
        cursor.moveToFirst()

        while(!cursor.isAfterLast) {
            val profile= DoDoProto.Profile.newBuilder()
            profile.pid = cursor.getInt(cursor.getColumnIndex(PROFILE_ID))
            profile.name = cursor.getString(cursor.getColumnIndex(PROFILE_NAME))
            //ToDO: datetime
            profileArray.add(profile.build())
            cursor.moveToNext()
        }
        cursor.close()
        return profileArray
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isProfileNameAvailable(name: String): Boolean {
        val profiles = getAllProfiles()
        for (profile in profiles) {
            if(name == profile.name) return false
        }
        return true
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "dodo.db"
        const val TODO_TABLE_NAME = "Todo"
        const val NOTE_TABLE_NAME = "Note"
        const val PROFILE_TABLE_NAME = "Profile"
        const val TAG_TABLE_NAME = "Tag"
        const val NOTE_TAG_REL_TABLE_NAME = "NoteTagRel"

        const val TASK_ID = "t_id"
        const val TASK_CREATOR_ID = "creator_id"
        const val TASK_TEXT = "text"
        const val TASK_COLOR = "color"
        const val TASK_DONE = "is_done"

        const val NOTE_ID = "n_id"
        const val NOTE_CREATOR_ID = "creator_id"
        const val NOTE_TITLE = "title"
        const val NOTE_CONTENT= "content"
        const val NOTE_VISIBLE = "is_visible"
        const val NOTE_HIGHLIGHTED = "is_highlighted"
        const val NOTE_COLOR = "color"
        const val NOTE_DATE = "creation_date"

        const val PROFILE_ID = "id"
        const val PROFILE_PASSWORD = "password" //ToDo: hashing?
        const val PROFILE_NAME = "name"
        const val PROFILE_DATE = "creation_date"

        const val TAG_ID = "ta_id"
        const val TAG_NAME = "name"

        const val NOTE_TAG_REL_NID = "n_id"
        const val NOTE_TAG_REL_TAID = "ta_id"
    }
}