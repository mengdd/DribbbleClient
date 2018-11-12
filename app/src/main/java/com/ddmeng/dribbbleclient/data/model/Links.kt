package com.ddmeng.dribbbleclient.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Links {

    @SerializedName("web")
    @Expose
    var web: String? = null
    @SerializedName("twitter")
    @Expose
    var twitter: String? = null
}