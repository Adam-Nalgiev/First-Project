package com.nadev.finalwork.data.data_classes.responses

data class RefreshTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: String,
    val scope: String
)