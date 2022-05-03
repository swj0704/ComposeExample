package com.wonjoon.domain

data class TodoItem(
    val id : Int,
    val title : String,
    val text : String,
    val createdAt : Long,
    val done : Boolean
)