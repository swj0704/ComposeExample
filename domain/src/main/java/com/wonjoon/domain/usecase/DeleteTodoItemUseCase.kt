package com.wonjoon.domain.usecase

import com.wonjoon.domain.TodoRepository
import com.wonjoon.domain.UseCase

class DeleteTodoItemUseCase (private val toDoRepository: TodoRepository) :
    UseCase {
    suspend operator fun invoke(id : Int) {
        return toDoRepository.deleteToDoItem(id)
    }
}