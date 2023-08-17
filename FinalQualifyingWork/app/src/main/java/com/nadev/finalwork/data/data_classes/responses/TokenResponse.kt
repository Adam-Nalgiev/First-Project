package com.nadev.finalwork.data.data_classes.responses

data class TokenResponse(
    val access_token: String,
    val idToken:String,
    val refresh_token: String
)