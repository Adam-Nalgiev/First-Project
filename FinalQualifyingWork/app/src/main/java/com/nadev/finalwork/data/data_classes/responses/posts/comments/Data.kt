package com.nadev.finalwork.data.data_classes.responses.posts.comments

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.data_classes.responses.posts.comments.Comment

data class Data (
    @SerializedName("after"      ) var after     : String?             = null,
    @SerializedName("dist"       ) var dist      : String?             = null,
    @SerializedName("modhash"    ) var modhash   : String?             = null,
    @SerializedName("geo_filter" ) var geoFilter : String?             = null,
    @SerializedName("children"   ) var children  : ArrayList<Comment> = arrayListOf(),
    @SerializedName("before"     ) var before    : String?             = null
        )