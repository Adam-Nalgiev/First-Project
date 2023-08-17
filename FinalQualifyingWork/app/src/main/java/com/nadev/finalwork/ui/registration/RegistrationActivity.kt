package com.nadev.finalwork.ui.registration

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nadev.finalwork.data.retrofitReg
import com.nadev.finalwork.databinding.RegistrationActivityBinding
import com.nadev.finalwork.ui.mainActivity.MainActivity
import com.nadev.finalwork.ui.onboarding.preferences
import kotlinx.coroutines.launch

const val IS_AUTHORIZED = "isAuthorized"
const val TOKEN = "Access Token"
const val client_id = "iJ8fYppvhHxCZ1h3185IaQ"
var accessToken = ""
class RegistrationActivity : AppCompatActivity() {
    private var refreshToken = ""
    private var authorCode = ""

    private val preferenceEditor = preferences.edit()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = RegistrationActivityBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()
        if (accessToken.length > 2) {
            preferenceEditor.putBoolean(IS_AUTHORIZED, true)
            preferenceEditor.apply()
        }
        b.authButton.setOnClickListener {
            openBrowser()
        }
        handleDeepLink(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            handleDeepLink(intent)
            Log.d("DEEPLINKURL NEW INTENT", intent.dataString!!)
        }
    }

    private fun openBrowser() {
        val intent = Intent(Intent.ACTION_VIEW, composeUrl())
        startActivity(intent)
    }

    private fun composeUrl(): Uri =
        Uri.parse("https://www.reddit.com/api/v1/authorize")
            .buildUpon()
            .appendQueryParameter("client_id", client_id)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("state", "finalpracticeworkapp")
            .appendQueryParameter("redirect_uri", "com.nadev.finalwork://auth")
            .appendQueryParameter("duration", "permanent")
            .appendQueryParameter("scope", "identity edit flair history modconfig modflair modlog " +
                    "modposts modwiki mysubreddits privatemessages read report save submit subscribe " +
                    "vote wikiedit wikiread")
            .build()

    private fun handleDeepLink(intent: Intent) {
        Log.d("HANDLE DEEP LINK", "DEEP LINK STARTED")
        if (intent.action != Intent.ACTION_VIEW) return
        val deepLinkUrl = intent.data ?: return
        if (deepLinkUrl.queryParameterNames.contains("error")) {
            Toast.makeText(this, "ERROR OF REQUEST", Toast.LENGTH_SHORT).show()
        }
        Log.d("DEEPLINKURL", intent.dataString!!)
        Log.d("IS CONTAINS CODE", deepLinkUrl.getQueryParameter("code").toString())
        authorCode = deepLinkUrl.getQueryParameter("code") ?: return
        getToken()
    }

    private fun getToken() {
        Log.d("GET TOKEN PROCESS", "GET TOKEN PROCESS STARTED")
        lifecycleScope.launch {
            Log.d("CODE", authorCode)
            val authString = "${client_id}:${""}"
            val encodedString = Base64.encodeToString(authString.toByteArray(), Base64.NO_WRAP)
            kotlin.runCatching {
                retrofitReg.getAccessToken(clientId =  "Basic $encodedString", grantType = "authorization_code", code = authorCode, redirectUri = "com.nadev.finalwork://auth")
            }.fold(
                onSuccess = {
                    refreshToken = it.refresh_token
                    accessToken = it.access_token  //Base64.decode(it.access_token, Base64.NO_WRAP).toString()
                    Log.d("GET TOKEN PROCESS", accessToken)
                    preferenceEditor.putBoolean(IS_AUTHORIZED, true)
                    preferenceEditor.putString(TOKEN, accessToken)
                    preferenceEditor.apply()
                    val intentToNext = Intent(this@RegistrationActivity, MainActivity::class.java)
                    startActivity(intentToNext)
                },
                onFailure = {
                    //    refreshToken(accessToken)
                    Log.d("ERROR", "ERROR OF GET TOKEN PROCESS ${it.message}")
                }
            )
        }
    }

    private fun refreshToken(tokenForRefresh: String) {
        lifecycleScope.launch {
            kotlin.runCatching {
                retrofitReg.refreshToken("refresh_token", tokenForRefresh)
            }.fold(
                onSuccess = {
                    accessToken = it.access_token
                    preferenceEditor.putString(TOKEN, accessToken)
                    preferenceEditor.apply()
                },
                onFailure = {
                    getToken()
                    Log.d("ERROR", "REFRESH TOKEN IS FAILURE")
                    refreshToken(refreshToken)
                }
            )
        }
    }
}
