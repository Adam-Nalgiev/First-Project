package com.nadev.finalwork.data.models

data class TokenResponse(
    val access_token: String,
    val idToken:String,
    val refresh_token: String
)