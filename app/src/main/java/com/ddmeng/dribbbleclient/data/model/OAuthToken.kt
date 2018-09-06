package com.ddmeng.dribbbleclient.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OAuthToken {

    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null
    @SerializedName("token_type")
    @Expose
    var tokenType: String? = null
    @SerializedName("scope")
    @Expose
    var scope: String? = null
}