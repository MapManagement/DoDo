package com.example.dodo


interface ItemListener {
    fun onItemStatusChanged(iteObjectId: String, isDone: Boolean)
    fun onItemDeleted(itemObjectId: String)
}