package com.nadev.finalwork.ui.mainActivity.fragments.openedProfile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadev.finalwork.data.models.user_info.UserInfo
import com.nadev.finalwork.data.retrofit
import kotlinx.coroutines.launch

var authorName = ""

class OpenedProfileViewModel : ViewModel() {

    fun getUserProfile(): UserInfo? {
        var user:UserInfo? = UserInfo()
        viewModelScope.launch {
            kotlin.runCatching {
                retrofit.user(userName = authorName)
            }.fold(onFailure = { Log.d("USER PROFILE", "GET USER PROFILE PROCESS IS FAILURE") },
                onSuccess = {user = it.data
                    it.data?.subreddit
                    Log.d("USER PROFILE", "GET USER PROFILE PROCESS IS SUCCESS")})
        }
        return user
    }
}