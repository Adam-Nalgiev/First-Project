package com.nadev.finalwork.data.data_classes.responses.subreddit

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.data_classes.responses.subreddit.Data


data class SubredditsResponse (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : Data?   = Data()

)