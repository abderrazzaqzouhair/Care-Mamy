package com.app.caremama

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.caremama.onboarding.OnboardingActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale


class SplashScreenActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreenBackgroundColor = if (false) R.color.primary_boy else R.color.primary_girl
        window.setBackgroundDrawableResource(R.color.secondary)
        installSplashScreen()
        setContentView(R.layout.activity_splash_screen)
        window.statusBarColor = ContextCompat.getColor(this, R.color.secondary)
//        FirebaseMessaging.getInstance().subscribeToTopic("allUsers")
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("FCM", "Subscribed to allUsers topic")
//                } else {
//                    Log.e("FCM", "Topic subscription failed", task.exception)
//                }
//            }
        val currentUser =  FirebaseAuth.getInstance().currentUser
        val sharedPrefs = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean("isDarkMode", false)
        val language = sharedPrefs.getString("language", "en")
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        if (language != null) {
            setAppLocale(language)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
            }
        }, 400)


    }

    private fun setAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources: Resources = resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}