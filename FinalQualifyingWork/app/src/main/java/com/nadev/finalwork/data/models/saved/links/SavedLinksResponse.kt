package com.nadev.finalwork.data.models.saved.links

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.models.saved.links.Data


data class SavedLinksResponse (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : Data?   = Data()

)