package com.nadev.finalwork.ui.mainActivity.fragments.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadev.finalwork.data.models.me.MeResponse
import com.nadev.finalwork.data.retrofit
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    fun getProfile():MeResponse{
        var profile = MeResponse()
        viewModelScope.launch {
            kotlin.runCatching {
                retrofit.me()
            }.fold(
                onSuccess = {
                    Log.d("PROFILE SUCCESS","GET PROFILE PROCESS IS SUCCESS")
                    profile = it
                },
                onFailure = {Log.d("PROFILE FAILED","GET PROFILE PROCESS IS FAILED") }
            )
        }
        return profile
    }

}