package com.nadev.finalwork.data.models.saved.links

import com.nadev.finalwork.data.models.saved.links.LinkData
import com.google.gson.annotations.SerializedName


data class SavedLink (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var linkData : LinkData?   = LinkData()

)