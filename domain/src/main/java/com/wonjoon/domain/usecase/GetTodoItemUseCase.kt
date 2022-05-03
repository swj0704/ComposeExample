package com.wonjoon.domain.usecase

import com.wonjoon.domain.TodoItem
import com.wonjoon.domain.TodoRepository
import com.wonjoon.domain.UseCase

class GetTodoItemUseCase (private val toDoRepository: TodoRepository) : UseCase {
    suspend operator fun invoke(id : Int) : TodoItem? {
        return toDoRepository.getToDoItem(id)
    }
}