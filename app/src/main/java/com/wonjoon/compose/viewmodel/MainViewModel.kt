package com.wonjoon.compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wonjoon.domain.TodoItem
import com.wonjoon.domain.usecase.DeleteTodoListUseCase
import com.wonjoon.domain.usecase.GetTodoListUseCase
import com.wonjoon.domain.usecase.UpdateTodoUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    val getTodoListUseCase: GetTodoListUseCase,
    val deleteTodoListUseCase: DeleteTodoListUseCase,
    val updateTodoUseCase: UpdateTodoUseCase
) : ViewModel() {

    private val _todoList = MutableLiveData<List<TodoItem>>(listOf())
    val todoList : LiveData<List<TodoItem>>
        get() = _todoList

    private val _errorEvent = MutableLiveData<Boolean>(false)
    val errorEvent : LiveData<Boolean>
        get() = _errorEvent

    fun getList() {
        viewModelScope.launch {
            try {
                _todoList.value = getTodoListUseCase()!!
                _errorEvent.value = getTodoListUseCase().isNullOrEmpty()
            } catch (e : Exception){
                _errorEvent.value = true
            }
        }
    }

    fun updateCheck(todoItem : TodoItem){
        viewModelScope.launch {
            try{
                updateTodoUseCase(todoItem)
                getList()
            } catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            try{
                deleteTodoListUseCase()
                getList()
            } catch (e : Exception){
                _errorEvent.value = true
            }
        }
    }
}