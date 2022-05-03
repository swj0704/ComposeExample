package com.wonjoon.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val title : String,
    val text : String,
    val createdAt : Long,
    val done : Boolean
)