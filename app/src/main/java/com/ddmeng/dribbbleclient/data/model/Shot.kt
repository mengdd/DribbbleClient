package com.ddmeng.dribbbleclient.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Shot {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("width")
    @Expose
    var width: Int? = null
    @SerializedName("height")
    @Expose
    var height: Int? = null
    @SerializedName("images")
    @Expose
    var images: Images? = null
    @SerializedName("published_at")
    @Expose
    var publishedAt: String? = null
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
    @SerializedName("html_url")
    @Expose
    var htmlUrl: String? = null
    @SerializedName("animated")
    @Expose
    var animated: Boolean? = null
    @SerializedName("tags")
    @Expose
    var tags: List<String>? = null
    @SerializedName("attachments")
    @Expose
    var attachments: List<Attachment>? = null
    @SerializedName("projects")
    @Expose
    var projects: List<Project>? = null
    @SerializedName("team")
    @Expose
    var team: Team? = null
    @SerializedName("low_profile")
    @Expose
    var lowProfile: Boolean? = null
}