package com.nadev.finalwork.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentsDB (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:String,
    @ColumnInfo(name = "name")
    val name:String?,
    @ColumnInfo(name = "description")
    val description:String?
)