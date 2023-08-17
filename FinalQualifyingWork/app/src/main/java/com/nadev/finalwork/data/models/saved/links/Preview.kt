package com.nadev.finalwork.data.models.saved.links

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.models.saved.links.Images


data class Preview (

    @SerializedName("images"  ) var images  : ArrayList<Images> = arrayListOf(),
    @SerializedName("enabled" ) var enabled : Boolean?          = null

)