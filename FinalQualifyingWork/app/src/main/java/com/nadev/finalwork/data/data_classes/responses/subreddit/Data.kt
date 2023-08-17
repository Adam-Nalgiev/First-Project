package com.nadev.finalwork.data.data_classes.responses.subreddit

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.data_classes.responses.subreddit.Children


data class Data (

    @SerializedName("after"      ) var after     : String?             = null,
    @SerializedName("dist"       ) var dist      : Int?                = null,
    @SerializedName("modhash"    ) var modhash   : String?             = null,
    @SerializedName("geo_filter" ) var geoFilter : String?             = null,
    @SerializedName("children"   ) var children  : ArrayList<Children> = arrayListOf(),
    @SerializedName("before"     ) var before    : String?             = null

)