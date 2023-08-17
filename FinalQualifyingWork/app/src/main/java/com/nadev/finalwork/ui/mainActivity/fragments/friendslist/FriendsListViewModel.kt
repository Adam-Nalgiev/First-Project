package com.nadev.finalwork.ui.mainActivity.fragments.friendslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadev.finalwork.data.models.friends.Friend
import com.nadev.finalwork.data.models.friends.FriendsResponse
import com.nadev.finalwork.data.retrofit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FriendsListViewModel: ViewModel() {

    private var _listFlow = MutableStateFlow<ArrayList<Friend>?>(arrayListOf())
    val listFlow = _listFlow.asStateFlow()

    init {
        getFriendsList()
    }
    private fun getFriendsList(){
        viewModelScope.launch {
            kotlin.runCatching {
                retrofit.friendsList()
            }.fold(
                onSuccess = { Log.d("GET FRIENDS LIST SUCCESS", "GET FRIENDS LIST PROCESS IS SUCCESS")
                            _listFlow.value = it.data?.friends},
                onFailure = {Log.d("GET FRIENDS LIST FAILURE", "GET FRIENDS LIST PROCESS IS FAILURE $it")}
            )
        }
    }
}