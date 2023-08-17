package com.nadev.finalwork.data.data_classes.responses.saved.comments

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.data_classes.responses.saved.comments.Data


data class SavedCommentsResponse (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : Data?   = Data()

)