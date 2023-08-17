package com.nadev.finalwork.data.data_classes.responses.posts.comments.utils

import com.nadev.finalwork.data.data_classes.responses.posts.SubredditPostsResponse
import com.nadev.finalwork.data.data_classes.responses.posts.comments.CommentsResponse
import com.nadev.finalwork.data.data_classes.responses.posts.comments.PostCommentsResponse
import com.google.gson.*
import java.lang.reflect.Type


internal class PostCommentsResponseDeserializer : JsonDeserializer<PostCommentsResponse?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PostCommentsResponse {
        val gson = Gson()
        val jsonArray = json.asJsonArray
        val post = gson.fromJson(jsonArray[0], SubredditPostsResponse::class.java)
        val commentsResponse = gson.fromJson(jsonArray[1], CommentsResponse::class.java)
        return PostCommentsResponse(post, commentsResponse)
    }
}