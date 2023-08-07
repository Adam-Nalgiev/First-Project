package com.nadev.finalwork.ui.mainActivity.fragments.subreddits

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadev.finalwork.data.retrofit
import com.nadev.finalwork.entity.SubredditsPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SubredditsViewModel : ViewModel() {
    private var _subsFlow = MutableStateFlow<List<SubredditsPreview>>(emptyList())
    val subsFlow = _subsFlow.asStateFlow()

    init {
        setSubs()
    }

    private fun setSubs() {
        viewModelScope.launch {
            runCatching {
                retrofit.getNewSubreddits()
            }.fold(onSuccess = {
                Log.d("GET SUBS PROCESS", "GET SUBS IS SUCCESS")
                _subsFlow.value = it
            },
                onFailure = { Log.d("GET SUBS PROCESS", "GET SUBS IS FAILURE") })
        }
    }
}