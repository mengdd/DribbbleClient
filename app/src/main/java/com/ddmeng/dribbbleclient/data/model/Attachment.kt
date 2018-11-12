package com.ddmeng.dribbbleclient.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Attachment {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("thumbnail_url")
    @Expose
    var thumbnailUrl: String? = null
    @SerializedName("size")
    @Expose
    var size: Int? = null
    @SerializedName("content_type")
    @Expose
    var contentType: String? = null
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
}