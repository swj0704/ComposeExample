package com.wonjoon.data.dao

import androidx.room.*
import com.wonjoon.data.model.Todo

@Dao
interface TodoDao {

    @Query("SELECT * FROM Todo")
    suspend fun getAll(): List<Todo>

    @Query("SELECT * FROM Todo WHERE id=:id")
    suspend fun getById(id: Int): Todo?

    @Insert
    suspend fun insert(toDoEntity: Todo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDoEntityList: List<Todo>)

    @Query("DELETE FROM Todo WHERE id=:id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM Todo")
    suspend fun deleteAll()

    @Update
    suspend fun update(toDoEntity: Todo)
}