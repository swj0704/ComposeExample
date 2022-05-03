package com.wonjoon.domain.usecase

import com.wonjoon.domain.TodoItem
import com.wonjoon.domain.TodoRepository
import com.wonjoon.domain.UseCase

class PostTodoListUseCase (private val toDoRepository: TodoRepository) : UseCase {
    suspend operator fun invoke(todoList : List<TodoItem>) {
        return toDoRepository.insertToDoList(todoList)
    }
}