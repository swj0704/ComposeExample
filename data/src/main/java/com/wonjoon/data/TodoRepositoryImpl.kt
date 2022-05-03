package com.wonjoon.data

import com.wonjoon.data.dao.TodoDao
import com.wonjoon.domain.TodoItem
import com.wonjoon.domain.TodoRepository


class TodoRepositoryImpl(private val toDoDao: TodoDao) : TodoRepository{

    override suspend fun getToDoList(): List<TodoItem> {
        return TodoMapper.getTodoItemList(toDoDao.getAll())
    }

    override suspend fun getToDoItem(id: Int): TodoItem? {
        return toDoDao.getById(id)?.let { TodoMapper.getTodoItem(it) }
    }

    override suspend fun insertToDoItem(toDoEntity: TodoItem): Long = toDoDao.insert(
        TodoMapper.getTodo(
            toDoEntity
        )
    )

    override suspend fun insertToDoList(toDoList: List<TodoItem>) = toDoDao.insert(
        TodoMapper.getTodoList(
            toDoList
        )
    )

    override suspend fun updateToDoItem(toDoEntity: TodoItem) = toDoDao.update(
        TodoMapper.getUpdateTodo(
            toDoEntity
        )
    )

    override suspend fun deleteToDoItem(id: Int) = toDoDao.delete(id)

    override suspend fun deleteAll() = toDoDao.deleteAll()

}