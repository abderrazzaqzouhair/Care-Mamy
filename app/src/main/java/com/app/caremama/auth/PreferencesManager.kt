package com.app.hydratracker.ui.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesManager(context: Context) {
     val prefs: SharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)


    fun saveUserDataToPreferences(username:String,email:String,birth:String) {
        prefs.edit() {
            putString("username", username)
            putString("email", email)
            putString("hearts", birth)
        }
    }
}
