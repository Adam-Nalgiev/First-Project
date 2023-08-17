package com.nadev.finalwork.data.data_classes.responses.user_info

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.data_classes.responses.user_info.UserInfo


data class UserInfoResponse (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : UserInfo?   = UserInfo()

)