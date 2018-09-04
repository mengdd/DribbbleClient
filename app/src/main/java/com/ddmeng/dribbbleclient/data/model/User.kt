package com.ddmeng.dribbbleclient.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.support.annotation.NonNull
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
@TypeConverters(DataTypeConverter::class)
data class User(
        @SerializedName("avatar_url")
        @Expose
        var avatarUrl: String? = null,
        @SerializedName("bio")
        @Expose
        var bio: String? = null,
        @SerializedName("can_upload_shot")
        @Expose
        var canUploadShot: Boolean? = null,
        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null,
        @SerializedName("followers_count")
        @Expose
        var followersCount: Int? = null,
        @SerializedName("html_url")
        @Expose
        var htmlUrl: String? = null,
        @PrimaryKey
        @SerializedName("id")
        @Expose
        @NonNull
        var id: Int = 0,
        @SerializedName("links")
        @Expose
        var links: Map<String, String>? = null,
        @SerializedName("location")
        @Expose
        var location: String? = null,
        @SerializedName("login")
        @Expose
        var login: String? = null,
        @SerializedName("name")
        @Expose
        var name: String? = null,
        @SerializedName("pro")
        @Expose
        var pro: Boolean? = null,
        @SerializedName("type")
        @Expose
        var type: String? = null,
        @SerializedName("teams")
        @Expose
        var teams: List<String>? = null

)
