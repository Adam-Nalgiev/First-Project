package com.nadev.finalwork.data.data_classes.responses.saved.links

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.data_classes.responses.saved.links.Data


data class SavedLinksResponse (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : Data?   = Data()

)