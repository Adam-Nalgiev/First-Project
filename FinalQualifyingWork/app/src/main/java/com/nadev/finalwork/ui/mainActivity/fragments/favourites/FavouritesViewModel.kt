package com.nadev.finalwork.ui.mainActivity.fragments.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nadev.finalwork.data.models.me.MeResponse
import com.nadev.finalwork.data.models.saved.comments.SavedComment
import com.nadev.finalwork.data.models.saved.links.SavedLink
import com.nadev.finalwork.data.retrofit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavouritesViewModel : ViewModel() {
    private val _pagedPosts= MutableStateFlow<Flow<PagingData<SavedLink>>?>(null)
    private val _pagedComments= MutableStateFlow<Flow<PagingData<SavedComment>>?>(null)
    private val _userInfo = MutableStateFlow<MeResponse?>(null)
    val pagedPosts = _pagedPosts.asStateFlow()
    val pagedComments = _pagedComments.asStateFlow()

    fun loadPosts(){
        viewModelScope.launch {
            if (_userInfo.value==null){
                _userInfo.value = retrofit.me()
                _userInfo.value?.name?.let { name->
                    SavedPostPagingSource().usernameInit(name)
                    SavedCommentPagingSource().usernameInit(name)
                }

            }
            _pagedPosts.value = Pager(
                config = PagingConfig(pageSize = SavedPostPagingSource.PAGE_SIZE),
                pagingSourceFactory = { SavedPostPagingSource() }
            ).flow
        }
    }
    fun loadComments(){
        viewModelScope.launch {
            if (_userInfo.value==null){
                _userInfo.value = retrofit.me()
                _userInfo.value?.name?.let { name->
                    SavedPostPagingSource().usernameInit(name)
                    SavedCommentPagingSource().usernameInit(name)
                }

            }
            _pagedComments.value = Pager(
                config = PagingConfig(pageSize = SavedCommentPagingSource.PAGE_SIZE),
                pagingSourceFactory = { SavedCommentPagingSource() }
            ).flow
        }

    }
}