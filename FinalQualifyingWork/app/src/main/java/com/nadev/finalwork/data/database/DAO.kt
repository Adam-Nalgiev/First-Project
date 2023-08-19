package com.nadev.finalwork.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nadev.finalwork.data.models.CommentsDB

@Dao
interface DAO {

    @Query("SELECT * FROM comments")
    suspend fun getAll(): List<CommentsDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(comment:CommentsDB)

    @Query("DELETE FROM comments")
    suspend fun deleteAll()

}