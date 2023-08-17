package com.nadev.finalwork.data.data_classes.responses.posts

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.data_classes.responses.posts.Data


data class SubredditPostsResponse (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : Data?   = Data()

)