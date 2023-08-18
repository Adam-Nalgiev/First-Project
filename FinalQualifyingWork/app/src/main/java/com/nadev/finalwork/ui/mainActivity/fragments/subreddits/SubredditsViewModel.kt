package com.nadev.finalwork.ui.mainActivity.fragments.subreddits

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadev.finalwork.data.models.subreddit.Children
import com.nadev.finalwork.data.retrofit
import com.nadev.finalwork.data.models.PageTypes
import com.nadev.finalwork.data.models.posts.comments.Comment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SubredditsViewModel : ViewModel() {
    private var _subsFlow = MutableStateFlow<ArrayList<Children>?>(arrayListOf())
    val subsFlow = _subsFlow.asStateFlow()

    init {
        setSubs(PageTypes.NewSubs)
    }

    fun setSubs(activePage: PageTypes){
        if (activePage == PageTypes.PopularSubs){
            setPopularSubs()
        }else{
            setNewSubs()
        }
    }

    private fun setNewSubs() {
        viewModelScope.launch {
            runCatching {
                retrofit.getNewSubreddits()
            }.fold(onSuccess = {
                Log.d("GET SUBS PROCESS", "GET SUBS IS SUCCESS $it")
                _subsFlow.value = arrayListOf()
                _subsFlow.value = it.data?.children
            },
                onFailure = { Log.d("GET SUBS PROCESS", "GET SUBS IS FAILURE $it") })
        }
    }

    private fun setPopularSubs(){
        viewModelScope.launch {
            runCatching {
                retrofit.getPopularSubreddits()
            }.fold(onSuccess = {
                Log.d("GET SUBS PROCESS", "GET Popular SUBS IS SUCCESS")
                _subsFlow.value = arrayListOf()
                _subsFlow.value = it.data?.children
            },
                onFailure = { Log.d("GET SUBS PROCESS", "GET popular SUBS IS FAILURE") })
        }
    }

    fun setFoundSubs(requestText:String){
        viewModelScope.launch {
            kotlin.runCatching {
                retrofit.searchSubreddits(q = requestText)
            }.fold(
                onSuccess = {
                    _subsFlow.value = arrayListOf()
                    _subsFlow.value = it.data?.children
                },
                onFailure = {
                    Log.d("FAILURE", "FAILURE GET FOUND SUBS PROCESS")
                }
            )
        }
    }
}