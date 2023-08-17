package com.nadev.finalwork.ui.mainActivity.fragments.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadev.finalwork.data.retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavouritesViewModel : ViewModel() {

    private var _subsFavouritesFlow = MutableStateFlow<List<String>>(emptyList())
    val subsFavouritesFlow = _subsFavouritesFlow.asStateFlow()

    private var _savedFavouritesFlow = MutableStateFlow<List<String>>(emptyList())
    val savedFavouritesFlow = _savedFavouritesFlow.asStateFlow()

    private var _commentsFavouritesFlow = MutableStateFlow<List<String>>(emptyList())
    val commentsFavouritesFlow = _commentsFavouritesFlow.asStateFlow()

    init {
        setSubs()
        setSaved()
        setComments()
    }

    private fun setSubs(){
        viewModelScope.launch {
            kotlin.runCatching {
             //   retrofit.getFavouritesSubs()
            }.fold(
                onSuccess = {
           //         _savedFavouritesFlow.value = it
                }, onFailure = { Log.d("FAILURE", "GET FAV SUBS PROCESS FAILED")}
            )
        }
    }
    private fun setSaved(){
        viewModelScope.launch {
            kotlin.runCatching {
        //        retrofit.getFavouritesSaved()
            }.fold(
                onSuccess = {
             //       _savedFavouritesFlow.value = it
                }, onFailure = { Log.d("FAILURE", "GET FAV SAVED SUBS PROCESS FAILED")}
            )
        }
    }
    private fun setComments(){
        viewModelScope.launch {
            kotlin.runCatching {
        //        retrofit.getFavouritesComments()
            }.fold(
                onSuccess = {
              //      _commentsFavouritesFlow.value = it
                }, onFailure = { Log.d("FAILURE", "GET FAV COMMENTS PROCESS FAILED")}
            )
        }
    }
}