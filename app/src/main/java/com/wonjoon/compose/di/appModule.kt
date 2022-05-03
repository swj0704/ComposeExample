package com.wonjoon.compose.di

import android.content.Context
import androidx.room.Room
import com.wonjoon.data.TodoDataBase
import com.wonjoon.data.TodoRepositoryImpl
import com.wonjoon.data.dao.TodoDao
import com.wonjoon.domain.TodoRepository
import com.wonjoon.domain.usecase.*
import com.wonjoon.compose.viewmodel.EditTodoVIewModel
import com.wonjoon.compose.viewmodel.MainViewModel
import com.wonjoon.compose.viewmodel.WriteTodoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideDataBase(androidContext()) }
    single { provideDao(get()) }
    single {
        val todoRepository : TodoRepository = TodoRepositoryImpl(get())
        todoRepository
    }

    factory { GetTodoItemUseCase(get()) }
    factory { GetTodoListUseCase(get()) }
    factory { PostTodoListUseCase(get()) }
    factory { PostTodoUseCase(get()) }
    factory { UpdateTodoUseCase(get()) }
    factory { DeleteTodoItemUseCase(get()) }
    factory { DeleteTodoListUseCase(get()) }

    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { WriteTodoViewModel(get(), get()) }
    viewModel { EditTodoVIewModel(get(), get(), get()) }
}

fun provideDataBase(context : Context) : TodoDataBase {
    return Room.databaseBuilder(
        context,
        TodoDataBase::class.java, "database-name"
    ).build()
}

fun provideDao(dataBase: TodoDataBase) : TodoDao{
    return dataBase.getDao()
}