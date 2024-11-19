package com.kirill.notifyapplication.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotifyDbModel(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val name : String,
    val text : String,
    val enable : Boolean,
    val timeHour : Int,
    val timeMinute : Int
)
