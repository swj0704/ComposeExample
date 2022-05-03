package com.wonjoon.domain.usecase

import com.wonjoon.domain.TodoRepository
import com.wonjoon.domain.UseCase

class DeleteTodoListUseCase (private val toDoRepository: TodoRepository) :
    UseCase {
    suspend operator fun invoke() {
        return toDoRepository.deleteAll()
    }
}