package com.nadev.finalwork.ui.mainActivity.fragments.favourites

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nadev.finalwork.data.models.saved.links.SavedLink
import com.nadev.finalwork.data.retrofit

class SavedPostPagingSource: PagingSource<String, SavedLink>() {
    lateinit var username:String
    fun usernameInit(userName:String)
    {
        this.username = userName
    }
    override fun getRefreshKey(state: PagingState<String, SavedLink>): String = FIRST_PAGE
    override suspend fun load(params: LoadParams<String>): LoadResult<String, SavedLink> {
        val page = params.key?: FIRST_PAGE
        return kotlin.runCatching {
            retrofit.getSavedPosts(username = username, page = page)
        }.fold(
            onSuccess = {
                Log.d("MyLog",it.toString())
                if (it.data?.children?.size!!< PAGE_SIZE){
                    it.data?.children?.let { posts ->
                        LoadResult.Page(
                            data = posts,
                            prevKey = null,
                            nextKey =  null
                        )
                    }
                }
                else{
                    it.data?.children?.let { posts ->
                        LoadResult.Page(
                            data = posts,
                            prevKey = null,
                            nextKey =  it.data?.after?:""
                        )
                    }
                }

            },
            onFailure = { LoadResult.Error(it) }
        )!!
    }
    companion object{
        private const val FIRST_PAGE = ""
        const val PAGE_SIZE = 10
    }
}