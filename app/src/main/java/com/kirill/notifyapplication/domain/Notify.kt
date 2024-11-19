package com.kirill.notifyapplication.domain

import androidx.room.PrimaryKey
import kotlinx.datetime.LocalTime

data class Notify(
    val id : Int = 0,
    val name : String,
    val text : String,
    val enable : Boolean,
    val time : LocalTime
)
