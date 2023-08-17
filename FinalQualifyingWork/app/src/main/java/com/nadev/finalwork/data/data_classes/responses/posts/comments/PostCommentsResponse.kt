package com.nadev.finalwork.data.data_classes.responses.posts.comments

import com.nadev.finalwork.data.data_classes.responses.posts.SubredditPostsResponse
import com.nadev.finalwork.data.data_classes.responses.posts.comments.CommentsResponse

class PostCommentsResponse(
    val subredditPostsResponse: SubredditPostsResponse,
    val commentsResponse: CommentsResponse
)