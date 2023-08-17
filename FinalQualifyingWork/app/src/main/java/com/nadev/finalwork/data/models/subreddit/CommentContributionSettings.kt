package com.nadev.finalwork.data.models.subreddit

import com.google.gson.annotations.SerializedName


data class CommentContributionSettings (
    @SerializedName("allowed_media_types") var allowedMediaTypes               : ArrayList<String>            = arrayListOf()
)