package com.wonjoon.data

import com.wonjoon.data.model.Todo
import com.wonjoon.domain.TodoItem

object TodoMapper {
    fun getTodoItemList(todoList : List<Todo>) : List<TodoItem>{
        return todoList.toList().map {
            TodoItem(it.id, it.title, it.text, it.createdAt, it.done)
        }
    }

    fun getTodoItem(todo : Todo) : TodoItem{
        return TodoItem(todo.id, todo.title, todo.text, todo.createdAt, todo.done)
    }

    fun getTodo(todoItem: TodoItem) : Todo{
        return Todo(title = todoItem.title, text = todoItem.text, createdAt = todoItem.createdAt, done = todoItem.done)
    }

    fun getUpdateTodo(todoItem: TodoItem) : Todo{
        return Todo(id = todoItem.id, title = todoItem.title, text = todoItem.text, createdAt = todoItem.createdAt, done = todoItem.done)
    }

    fun getTodoList(todoList : List<TodoItem>) : List<Todo>{
        return todoList.toList().map {
            Todo(title = it.title, text = it.text, createdAt = it.createdAt, done = it.done)
        }
    }

}