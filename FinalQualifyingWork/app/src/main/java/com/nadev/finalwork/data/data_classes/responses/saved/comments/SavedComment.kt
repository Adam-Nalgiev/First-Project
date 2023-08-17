package com.nadev.finalwork.data.data_classes.responses.saved.comments

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.data_classes.responses.saved.comments.CommentData


data class SavedComment (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var commentData : CommentData?   = CommentData()

)