package com.nadev.finalwork.data.models.user_info

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.models.user_info.UserInfo


data class UserInfoResponse (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : UserInfo?   = UserInfo()

)