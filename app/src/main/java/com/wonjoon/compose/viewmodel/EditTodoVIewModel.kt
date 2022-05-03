package com.wonjoon.compose.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wonjoon.compose.utils.Event
import com.wonjoon.domain.TodoItem
import com.wonjoon.domain.usecase.DeleteTodoItemUseCase
import com.wonjoon.domain.usecase.GetTodoItemUseCase
import com.wonjoon.domain.usecase.UpdateTodoUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditTodoVIewModel(
    val getTodoItemUseCase: GetTodoItemUseCase,
    val updateTodoItemUseCase: UpdateTodoUseCase,
    val deleteTodoItemUseCase: DeleteTodoItemUseCase
) : ViewModel() {
    private val _errorEventChannel = Channel<Event>(Channel.BUFFERED)
    val events = _errorEventChannel.receiveAsFlow()

    private val _todoItem = MutableLiveData<TodoItem>(null)
    val todoItem : LiveData<TodoItem>
        get() = _todoItem

    fun getTodo(id : Int){
        viewModelScope.launch {
            try{
                _todoItem.value = getTodoItemUseCase(id)!!
            } catch (e : Exception){
                e.printStackTrace()
                _errorEventChannel.send(Event.ErrorEvent)
            }
        }
    }

    fun finish(){
        viewModelScope.launch {
            _errorEventChannel.send(Event.FinishEvent)
        }
    }

    fun updateTodo(todoItem : TodoItem){
        viewModelScope.launch {
            try {
                if(todoItem.title != "" && todoItem.text != "") {
                    Log.d("UPDATESUCCESS", todoItem.title)
                    updateTodoItemUseCase(todoItem)
                    _errorEventChannel.send(Event.FinishEvent)
                } else {
                    _errorEventChannel.send(Event.BlankEvent)
                }
            } catch (e : Exception){
                e.printStackTrace()
                _errorEventChannel.send(Event.ErrorEvent)
            }
        }
    }

    fun deleteTodo(id : Int){
        viewModelScope.launch {
            try {
                deleteTodoItemUseCase(id)
                _errorEventChannel.send(Event.FinishEvent)
            } catch (e : Exception){
                e.printStackTrace()
                _errorEventChannel.send(Event.ErrorEvent)
            }
        }
    }


}