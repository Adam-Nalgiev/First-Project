package com.nadev.finalwork.ui.mainActivity.fragments.openedSub

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadev.finalwork.data.models.posts.comments.Comment
import com.nadev.finalwork.data.retrofit
import com.nadev.finalwork.ui.mainActivity.fragments.subreddits.itemID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OpenedSubredditsViewModel : ViewModel() {
    private var _commentsFlow = MutableStateFlow<ArrayList<Comment>?>(arrayListOf())
    val commentsFlow = _commentsFlow.asStateFlow()

    init {
        setComs()
    }

    private fun setComs() {
        viewModelScope.launch {
            kotlin.runCatching {
                retrofit.getSubsComments(subreddit = itemID)
            }.fold(
                onFailure = { Log.d("SET COMMENTS", "SET COMMENTS IS FAILURE") }, onSuccess = {
                    Log.d("SET COMMENTS", "${it.commentsResponse?.data?.children}")
                    if (it.commentsResponse?.data != null && it.commentsResponse.data?.children != null) {
                        _commentsFlow.value = it.commentsResponse.data?.children
                    }else{
                        _commentsFlow.value = null
                    }
                }
            )
        }
    }
}