package com.nadev.finalwork.data.models.subreddit

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.models.subreddit.Data


data class SubredditsResponse (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : Data?   = Data()

)