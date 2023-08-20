package com.nadev.finalwork.data.models.posts.comments

import com.nadev.finalwork.data.models.posts.SubredditPostsResponse
import com.nadev.finalwork.data.models.posts.comments.CommentsResponse

class PostCommentsResponse(
    val subredditPostsResponse: SubredditPostsResponse,
    val commentsResponse: CommentsResponse?
)