package com.nadev.finalwork.data

import com.nadev.finalwork.entity.RefreshTokenResponse
import com.nadev.finalwork.entity.SubredditsPreview
import com.nadev.finalwork.entity.TokenResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RegAPI {
    @POST("/access_token")
    suspend fun getAccessToken(
        @Path("grant_type") grant_type: String,
        @Path("code") code: String,
        @Path("redirect_uri") redirect_uri: String
    ): TokenResponse

    @POST("/access_token")
    suspend fun refreshToken(
        @Path("grant_type") grant_type: String,
        @Path("refresh_token") refresh_token: String,
    ): RefreshTokenResponse
}

interface API {
    @GET("/users/popular")
    suspend fun getNewSubreddits() : List<SubredditsPreview>

  //  @GET("/api/v1/me")
  //  suspend fun getMyProfile():MyProfile

    //@GET("/prefs/friends")
   // suspend fun getFriendsList(): List<Friends>

    //@GET

}
/*DELETE /api/v1/me/friends/username
[/r/subreddit]/api/friend
[/r/subreddit]/api/unfriend
POST /api/subscribe
PUT /api/v1/me/friends/username


GET /users/
→ /users/popular
→ /users/new*/

val retrofitReg: RegAPI = Retrofit.Builder()
    .baseUrl("https://www.reddit.com/api/v1")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(RegAPI::class.java)

val retrofit: API = Retrofit.Builder()
    .baseUrl("https://oauth.reddit.com")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(API::class.java)


