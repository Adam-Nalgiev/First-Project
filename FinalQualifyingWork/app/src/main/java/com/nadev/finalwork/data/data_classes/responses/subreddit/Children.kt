package com.nadev.finalwork.data.data_classes.responses.subreddit

import com.google.gson.annotations.SerializedName


data class Children (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : SubredditData?   = SubredditData()

)