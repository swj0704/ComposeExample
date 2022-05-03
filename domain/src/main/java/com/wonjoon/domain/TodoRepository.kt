package com.wonjoon.domain


interface TodoRepository {
    suspend fun getToDoList(): List<TodoItem>

    suspend fun getToDoItem(id: Int): TodoItem?

    suspend fun insertToDoItem(toDoEntity: TodoItem): Long

    suspend fun insertToDoList(toDoList: List<TodoItem>)

    suspend fun updateToDoItem(toDoEntity: TodoItem)

    suspend fun deleteToDoItem(id: Int)

    suspend fun deleteAll()
}