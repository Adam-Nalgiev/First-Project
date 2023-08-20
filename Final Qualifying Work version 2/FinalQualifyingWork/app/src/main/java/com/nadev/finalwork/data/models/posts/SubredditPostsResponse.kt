package com.nadev.finalwork.data.models.posts

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.models.posts.Data


data class SubredditPostsResponse (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : Data?   = Data()

)