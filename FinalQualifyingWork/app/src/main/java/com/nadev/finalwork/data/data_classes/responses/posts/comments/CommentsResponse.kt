package com.nadev.finalwork.data.data_classes.responses.posts.comments

import com.google.gson.annotations.SerializedName

data class CommentsResponse (
    @SerializedName("kind" ) var kind : String? = null,
    @SerializedName("data" ) var data : Data?   = Data()
        )