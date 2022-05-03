package com.wonjoon.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wonjoon.data.dao.TodoDao
import com.wonjoon.data.model.Todo

@Database(entities = [Todo::class], version = 2, exportSchema = false)
abstract class TodoDataBase : RoomDatabase(){
    abstract fun getDao() : TodoDao
}