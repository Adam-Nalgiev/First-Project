package com.nadev.finalwork

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nadev.finalwork.data.database.AppDataBase
import com.nadev.finalwork.data.models.CommentsDB

class App : Application() {

    lateinit var db: AppDataBase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "db").build()
    }
}