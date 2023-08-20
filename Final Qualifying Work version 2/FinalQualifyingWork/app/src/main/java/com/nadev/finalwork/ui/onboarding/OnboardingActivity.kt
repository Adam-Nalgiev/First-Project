package com.nadev.finalwork.ui.onboarding

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nadev.finalwork.R
import com.nadev.finalwork.databinding.OnboardingActivityBinding
import com.nadev.finalwork.ui.mainActivity.MainActivity
import com.nadev.finalwork.ui.registration.IS_AUTHORIZED
import com.nadev.finalwork.ui.registration.RegistrationActivity
import com.nadev.finalwork.ui.registration.TOKEN
import com.nadev.finalwork.ui.registration.accessToken
import kotlinx.coroutines.launch
const val PREFERENCE_NAME = "App_Preference"
lateinit var preferences: SharedPreferences
class OnboardingActivity : AppCompatActivity() {

    private val listOfFragment = listOf(R.drawable.first_page, R.drawable.second_page, R.drawable.third_page)
    private var currentPage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        val authored = preferences.getBoolean(IS_AUTHORIZED, false)
        accessToken = preferences.getString(TOKEN, "")!!
        if (authored){
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
        super.onCreate(savedInstanceState)
        val b = OnboardingActivityBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(b.root)
        b.secondPoint.animate().alpha(0.4f).duration = 100
        b.thirdPoint.animate().alpha(0.4f).duration = 100
        b.image2.animate().alpha(0f)
        b.image3.animate().alpha(0f)
        b.image1.setImageResource(listOfFragment[0])
        b.next.setOnClickListener {
            lifecycleScope.launch {
                currentPage++
                when (currentPage) {
                    0 -> {
                        b.title.text = "${getText(R.string.onboarding_first_text_greetings)}"
                        b.description.text = "${getText(R.string.onboarding_first_text)}"
                    }

                    1 -> {
                        b.firstPoint.animate().apply {
                            duration = 500
                            alpha(0.4f)
                        }
                        b.image1.animate().apply {
                            duration = 500
                            alpha(0f)
                        }
                        b.secondPoint.animate().apply {
                            duration = 500
                            alpha(1f)
                        }
                        b.image2.animate().apply {
                            duration = 500
                            alpha(1f)
                        }
                        b.title.text = "${getText(R.string.onboarding_second_text_title)}"
                        b.description.text = "${getText(R.string.onboarding_second_text)}"
                    }

                    2 -> {
                        b.secondPoint.animate().apply {
                            duration = 500
                            alpha(0.4f)
                        }
                        b.image2.animate().apply {
                            duration = 500
                            alpha(0f)
                        }
                        b.thirdPoint.animate().apply {
                            alpha(1f)
                            duration = 500
                        }
                        b.image3.animate().apply {
                            duration = 500
                            alpha(1f)
                        }
                        b.title.text = "${getText(R.string.onboarding_third_text_title)}"
                        b.description.text = "${getText(R.string.onboarding_third_text)}"
                    }

                    else -> {
                        val regIntent =
                            Intent(this@OnboardingActivity, RegistrationActivity::class.java)
                        startActivity(regIntent)
                    }
                }
            }
        }
    }

}