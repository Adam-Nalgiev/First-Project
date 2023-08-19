package com.nadev.finalwork.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nadev.finalwork.data.models.RefreshTokenResponse
import com.nadev.finalwork.data.models.TokenResponse
import com.nadev.finalwork.data.models.friends.FriendsResponse
import com.nadev.finalwork.data.models.me.MeResponse
import com.nadev.finalwork.data.models.posts.SubredditPostsResponse
import com.nadev.finalwork.data.models.posts.comments.CommentsResponse
import com.nadev.finalwork.data.models.posts.comments.PostCommentsResponse
import com.nadev.finalwork.data.models.saved.comments.SavedCommentsResponse
import com.nadev.finalwork.data.models.saved.links.SavedLinksResponse
import com.nadev.finalwork.data.models.subreddit.SubredditsResponse
import com.nadev.finalwork.data.models.user_info.UserInfoResponse
import com.nadev.finalwork.ui.registration.accessToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("subreddits/new/")
    suspend fun getNewSubreddits(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Query("after") page: String = ""
    ): SubredditsResponse

    @POST("api/subscribe")
    suspend fun subscribe(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Query("action") action:String = "sub",
        @Query("action_source") actionSource: String = "r",
        @Query("skip_initial_defaults") skipInitialDefaults: Boolean = false,
        @Query("sr") sr: String
    )

    @POST("api/subscribe")
    suspend fun unsubscribe(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Query("action") action:String = "unsub",
        @Query("action_source") actionSource: String = "r",
        @Query("skip_initial_defaults") skipInitialDefaults: Boolean = false,
        @Query("sr") sr: String
    )

    @GET("r/{subreddit}/about")
    suspend fun getSubreddit(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Path("subreddit") subreddit: String
    ): SubredditPostsResponse

    @GET("r/{subreddit}/comments")
    suspend fun getSubsComments(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Path("subreddit") subreddit: String
    ): PostCommentsResponse

    @GET("subreddits/search")
    suspend fun searchSubreddits(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Query("q") q: String
    ): SubredditsResponse

    //Посты

    @GET("new")
    suspend fun getNewPosts(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Query("after") page: String,
    ): SubredditPostsResponse

    @GET("r/popular")
    suspend fun getPopularPosts(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Query("after") page: String,
    ): SubredditPostsResponse

    //Юзер

    @GET("api/v1/me")
    suspend fun me(
        @Header("Authorization") request: String? = "Bearer $accessToken"
    ): MeResponse

    @GET("api/v1/me/friends")
    suspend fun friendsList(
        @Header("Authorization") request: String? = "Bearer $accessToken"
    ): FriendsResponse

    @PUT("api/v1/me/friends/{username}")
    suspend fun addToFriends(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Path("username") username: String,
        @Body requestBody: String
    )

    @GET("user/{username}/where")
    suspend fun getUsersComments(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Path("username") username: String
    )

    @DELETE("api/v1/me/friends/{username}")
    suspend fun removeFromFriends(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Path("username") username: String
    )
    @GET("user/{username}/about")
    suspend fun user(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Path("username") userName: String
    ): UserInfoResponse

    //Сохраненное
    @GET("user/{username}/saved?type=links")
    suspend fun getSavedPosts(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Path("username") username:String,
        @Query("after")page:String,
        ): SavedLinksResponse
    @GET("user/{username}/saved?type=comments")
    suspend fun getSavedComments(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Path("username") username:String,
        @Query("after")page:String,
        ): SavedCommentsResponse

    @POST("api/save")
    suspend fun saveThing(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Query("category") category: String,
        @Query("id") id: String
    )

    @POST("api/unsave")
    suspend fun unsaveThing(
        @Header("Authorization") request: String? = "Bearer $accessToken",
        @Query("id") id: String
    )
}


var gson: Gson = GsonBuilder()
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
