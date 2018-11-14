package com.ddmeng.dribbbleclient.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@TypeConverters(DataTypeConverter::class)
@Entity(tableName = "team")
data class Team(

    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("login")
    @Expose
    var login: String? = null,
    @SerializedName("html_url")
    @Expose
    var htmlUrl: String? = null,
    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String? = null,
    @SerializedName("bio")
    @Expose
    var bio: String? = null,
    @SerializedName("location")
    @Expose
    var location: String? = null,
    @SerializedName("links")
    @Expose
    var links: Map<String, String>? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
)