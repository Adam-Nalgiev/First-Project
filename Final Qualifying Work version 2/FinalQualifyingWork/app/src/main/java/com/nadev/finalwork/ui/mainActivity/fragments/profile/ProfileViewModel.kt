package com.nadev.finalwork.ui.mainActivity.fragments.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nadev.finalwork.data.models.me.MeResponse
import com.nadev.finalwork.data.retrofit

class ProfileViewModel : ViewModel() {

    suspend fun getProfile(): MeResponse {
        val profile = kotlin.runCatching {
            retrofit.me()
        }.fold(
            onSuccess = {
                Log.d("PROFILE SUCCESS", "GET PROFILE PROCESS IS SUCCESS")
                it
            },
            onFailure = { Log.d("PROFILE FAILED", "GET PROFILE PROCESS IS FAILED")
            MeResponse(null,null,null,null,null)}
        )
        return profile
    }

}