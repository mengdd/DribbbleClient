package com.ddmeng.dribbbleclient.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
@TypeConverters(DataTypeConverter::class)
data class User(
    @SerializedName("id")
    @Expose
    @PrimaryKey
    var id: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("username")
    @Expose
    var username: String? = null,
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
    @SerializedName("buckets_count")
    @Expose
    var bucketsCount: Int? = null,
    @SerializedName("comments_received_count")
    @Expose
    var commentsReceivedCount: Int? = null,
    @SerializedName("followers_count")
    @Expose
    var followersCount: Int? = null,
    @SerializedName("followings_count")
    @Expose
    var followingsCount: Int? = null,
    @SerializedName("likes_count")
    @Expose
    var likesCount: Int? = null,
    @SerializedName("likes_received_count")
    @Expose
    var likesReceivedCount: Int? = null,
    @SerializedName("projects_count")
    @Expose
    var projectsCount: Int? = null,
    @SerializedName("rebounds_received_count")
    @Expose
    var reboundsReceivedCount: Int? = null,
    @SerializedName("shots_count")
    @Expose
    var shotsCount: Int? = null,
    @SerializedName("teams_count")
    @Expose
    var teamsCount: Int? = null,
    @SerializedName("can_upload_shot")
    @Expose
    var canUploadShot: Boolean? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("pro")
    @Expose
    var pro: Boolean? = null,
    @SerializedName("buckets_url")
    @Expose
    var bucketsUrl: String? = null,
    @SerializedName("followers_url")
    @Expose
    var followersUrl: String? = null,
    @SerializedName("following_url")
    @Expose
    var followingUrl: String? = null,
    @SerializedName("likes_url")
    @Expose
    var likesUrl: String? = null,
    @SerializedName("shots_url")
    @Expose
    var shotsUrl: String? = null,
    @SerializedName("teams_url")
    @Expose
    var teamsUrl: String? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
)