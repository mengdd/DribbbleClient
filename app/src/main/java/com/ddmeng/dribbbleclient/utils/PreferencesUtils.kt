package com.ddmeng.dribbbleclient.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesUtils @Inject constructor(appContext: Application) {
    companion object {
        private const val SHARED_PREFERENCE_NAME = "dribbble_client_shared_preferences"
        private const val USER_LOGGED_IN = "user_logged_in"
        private const val USER_TOKEN = "user_token"
    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = appContext.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    val isUserLoggedIn
        get() = sharedPreferences.getBoolean(USER_LOGGED_IN, false)
    val userToken: String
        get() = sharedPreferences.getString(USER_TOKEN, "")

    fun saveUserLoggedIn(isLoggedIn: Boolean) {
        LogUtils.d("save user logged in : $isLoggedIn")
        sharedPreferences.put {
            putBoolean(USER_LOGGED_IN, isLoggedIn)
        }
    }

    infix fun saveUserToken(token: String?) {
        LogUtils.d("save user token : $token")
        sharedPreferences.put {
            putString(USER_TOKEN, token)
        }
    }

    fun deleteToken() {
        LogUtils.d("delete user token")
        sharedPreferences.put {
            putString(USER_TOKEN, "")
        }
    }

}

private inline fun SharedPreferences.put(body: SharedPreferences.Editor.() -> Unit) {
    val editor = this.edit()
    editor.body()
    editor.apply()
}
