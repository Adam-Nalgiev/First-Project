package com.nadev.finalwork.entity

data class SubredditsPreview(
    val after: String,
    val before:String,
    val count:Int,
    val limit: Int = 100,
    val show:String,
    val detail:String
)
