package com.ddmeng.dribbbleclient.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Images {

    @SerializedName("hidpi")
    @Expose
    var hidpi: Any? = null
    @SerializedName("normal")
    @Expose
    var normal: String? = null
    @SerializedName("teaser")
    @Expose
    var teaser: String? = null
}