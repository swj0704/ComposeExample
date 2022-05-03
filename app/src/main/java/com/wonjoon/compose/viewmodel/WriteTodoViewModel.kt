package com.wonjoon.compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wonjoon.domain.TodoItem
import com.wonjoon.domain.usecase.GetTodoListUseCase
import com.wonjoon.domain.usecase.PostTodoUseCase
import kotlinx.coroutines.launch

class WriteTodoViewModel(val postTodoItemUseCase: PostTodoUseCase, val getTodoListUseCase: GetTodoListUseCase) : ViewModel(){

    private val _errorEvent = MutableLiveData<Boolean>(false)
    val errorEvent : LiveData<Boolean>
        get() = _errorEvent

    fun writeTodo(title : String, text : String){
        viewModelScope.launch {
            try{
                val list = getTodoListUseCase()
                val size = if(list.isNullOrEmpty()){
                    0
                } else {
                    list[list.size - 1].id + 1
                }

                postTodoItemUseCase(TodoItem(size, title, text, System.currentTimeMillis(), false))
                _errorEvent.value = true
            } catch (e : Exception){
                e.printStackTrace()
                _errorEvent.value = true
            }
        }
    }
}