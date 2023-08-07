package com.nadev.finalwork.entity

data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: String,
    val scope: String,
    val refresh_token: String
)