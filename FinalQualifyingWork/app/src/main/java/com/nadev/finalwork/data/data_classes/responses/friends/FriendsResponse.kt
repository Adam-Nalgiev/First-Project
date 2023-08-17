package com.nadev.finalwork.data.data_classes.responses.friends

import com.google.gson.annotations.SerializedName
import com.nadev.finalwork.data.data_classes.responses.friends.Data


data class FriendsResponse (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : Data?   = Data()

)