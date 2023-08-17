package com.nadev.finalwork.data.models.friends

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.models.friends.Data

data class FriendsResponse (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : Data?   = Data()

)