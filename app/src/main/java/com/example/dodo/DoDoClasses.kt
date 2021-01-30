package com.example.dodo

class ToDoTask {
    var taskID: Int = 0
    var taskText: String = ""
    var taskColor: String = ""
    var isDone: Boolean = false
    var isDeleted: Boolean = false
}

class Note {
    var noteID: Int = 0
    var noteText: String = ""
    var isVisible: Boolean = true
    var isHighlighted: Boolean = false
    var noteColor: String = "#84A5B9"
    var isDeleted: Boolean = false
}

class DoDoActivities {
    var MainAc: Boolean = false
    var NoteAc: Boolean = false
    var SettingsAc: Boolean = false
}