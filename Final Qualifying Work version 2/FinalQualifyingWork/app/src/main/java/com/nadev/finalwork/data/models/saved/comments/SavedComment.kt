package com.nadev.finalwork.data.models.saved.comments

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.models.saved.comments.CommentData


data class SavedComment (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var commentData : CommentData?   = CommentData()

)