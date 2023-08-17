package com.nadev.finalwork.data.models.posts.comments

import com.nadev.finalwork.data.models.saved.comments.CommentData
import com.google.gson.annotations.SerializedName


data class Comment (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : CommentData?   = CommentData()

)