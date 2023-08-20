package com.nadev.finalwork.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nadev.finalwork.data.models.CommentsDB

@Database(entities = [CommentsDB::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dao():DAO
}