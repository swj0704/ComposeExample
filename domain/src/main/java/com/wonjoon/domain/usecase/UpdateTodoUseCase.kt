package com.wonjoon.domain.usecase

import com.wonjoon.domain.TodoItem
import com.wonjoon.domain.TodoRepository
import com.wonjoon.domain.UseCase


class UpdateTodoUseCase (private val toDoRepository: TodoRepository) : UseCase {
    suspend operator fun invoke(todo : TodoItem){
        return toDoRepository.updateToDoItem(todo)
    }
}