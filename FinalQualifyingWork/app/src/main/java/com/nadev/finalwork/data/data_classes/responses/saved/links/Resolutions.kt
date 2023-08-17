package com.nadev.finalwork.data.data_classes.responses.saved.links

import com.google.gson.annotations.SerializedName


data class Resolutions (

  @SerializedName("url"    ) var url    : String? = null,
  @SerializedName("width"  ) var width  : Int?    = null,
  @SerializedName("height" ) var height : Int?    = null

)