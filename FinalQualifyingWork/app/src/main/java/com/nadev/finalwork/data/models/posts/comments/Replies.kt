package com.nadev.finalwork.data.models.posts.comments

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.models.posts.comments.Data


data class Replies (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : Data?   = Data()

)