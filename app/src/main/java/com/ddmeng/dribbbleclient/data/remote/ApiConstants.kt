package com.ddmeng.dribbbleclient.data.remote

import com.ddmeng.dribbbleclient.BuildConfig

object ApiConstants {
    const val CLIENT_ID = BuildConfig.DRIBBBLE_CLIENT_ID
    const val LOGIN_OAUTH_URL = "https://dribbble.com/oauth/authorize?client_id=$CLIENT_ID"
}