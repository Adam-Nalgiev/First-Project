package com.nadev.finalwork.data.repository

import com.google.gson.GsonBuilder
import com.nadev.finalwork.data.data_classes.responses.RefreshTokenResponse
import com.nadev.finalwork.data.data_classes.responses.TokenResponse
import com.nadev.finalwork.data.data_classes.responses.friends.FriendsResponse
import com.nadev.finalwork.data.data_classes.responses.me.MeResponse
import com.nadev.finalwork.data.data_classes.responses.posts.SubredditPostsResponse
import com.nadev.finalwork.data.data_classes.responses.saved.links.SavedLinksResponse
import com.nadev.finalwork.data.data_classes.responses.subreddit.SubredditsResponse
import com.nadev.finalwork.data.data_classes.responses.user_info.UserInfoResponse
import com.nadev.finalwork.ui.registration.accessToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RegAPI {
    @POST("access_token/")
    suspend fun getAccessToken(
        @Header("Authorization") clientId: String,
        @Query("grant_type") grantType: String,
        @Query("code") code: String,
        @Query("redirect_uri") redirectUri: String
    ): TokenResponse

    @POST("access_token")
    suspend fun refreshToken(
        @Path("grant_type") grantType: String,
        @Path("refresh_token") refreshToken: String,
    ): RefreshTokenResponse
}

interface API {

    //Сабреддиты
    @GET("subreddits/popular/")
    suspend fun getPopularSubreddits(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Query("after") page: String = ""
    ): SubredditsResponse

    @GET("r/{subreddit}")
    suspend fun getSubredditPosts(
        @Header("Authorization") authHeader: String,
        @Path("subreddit") subreddit: String
    ): SubredditPostsResponse

    @GET("comments/{post}")
    suspend fun getPostComments(
        @Header("Authorization") authHeader: String,
        @Path("post") post: String,
        @Query("limit") limit: Int
    ): String

    @GET("subreddits/search")
    suspend fun searchSubreddits(
        @Header("Authorization") authHeader: String,
        @Query("count") count: Int,
        @Query("limit") limit: Int,
        @Query("q") q: String
    ): SubredditsResponse

    //Посты

    @GET("new")
    suspend fun getNewPosts(
        @Header("Authorization") authHeader: String,
        @Query("after") page: String,
    ): SubredditPostsResponse

    @GET("r/popular")
    suspend fun getPopularPosts(
        @Header("Authorization") authHeader: String,
        @Query("after") page: String,
    ): SubredditPostsResponse

    //Юзер

    @GET("api/v1/me")
    suspend fun me(
        @Header("Authorization") authHeader: String
    ): MeResponse

    @GET("api/v1/me/friends")
    suspend fun friendsList(
        @Header("Authorization") authHeader: String
    ): FriendsResponse

    @PUT("api/v1/me/friends/{username}")
    suspend fun addToFriends(
        @Header("Authorization") authHeader: String,
        @Path("username") username: String,
        @Body requestBody: String,
    )

    @GET("user/{username}/about")
    suspend fun user(
        @Header("Authorization") authHeader: String,
        @Path("username") userName: String
    ): UserInfoResponse

    //Сохраненное

    @GET("user/{username}/saved?type=links")
    suspend fun getSavedPosts(
        @Header("Authorization") authHeader: String,
        @Path("username") username: String,
        @Query("after") page: String
    ): SavedLinksResponse

    @GET("user/{username}/saved?type=comments")
    suspend fun getSavedComments(
        @Header("Authorization") authHeader: String,
        @Path("username") username: String,
        @Query("after") page: String
    ): String

    @POST("api/save")
    suspend fun saveThing(
        @Header("Authorization") authHeader: String,
        @Query("category") category: String,
        @Query("id") id: String
    )

    @POST("api/unsave")
    suspend fun unsaveThing(
        @Header("Authorization") authHeader: String,
        @Query("id") id: String
    )
}


var gson = GsonBuilder()
    .setLenient()
    .create()

val retrofitReg: RegAPI = Retrofit.Builder()
    .baseUrl("https://www.reddit.com/api/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(RegAPI::class.java)

val retrofit: API = Retrofit.Builder()
    .baseUrl("https://oauth.reddit.com/")
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()
    .create(API::class.java)