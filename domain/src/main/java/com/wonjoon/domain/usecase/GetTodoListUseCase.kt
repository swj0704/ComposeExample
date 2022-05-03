package com.wonjoon.domain.usecase

import com.wonjoon.domain.TodoItem
import com.wonjoon.domain.TodoRepository
import com.wonjoon.domain.UseCase

class GetTodoListUseCase (private val toDoRepository: TodoRepository) :
    UseCase {
    suspend operator fun invoke() : List<TodoItem>{
        return toDoRepository.getToDoList()
    }
}