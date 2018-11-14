package com.ddmeng.dribbbleclient.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "shots_table")
@TypeConverters(DataTypeConverter::class)
data class Shot(
    @SerializedName("id")
    @Expose
    @PrimaryKey
    var id: Int? = null,
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @SerializedName("description")
    @Expose
    var description: String? = null,
    @SerializedName("width")
    @Expose
    var width: Int? = null,
    @SerializedName("height")
    @Expose
    var height: Int? = null,
    @SerializedName("images")
    @Expose
    @Ignore
    var images: Images? = null,
    @SerializedName("views_count")
    @Expose
    var viewsCount: Int? = null,
    @SerializedName("likes_count")
    @Expose
    var likesCount: Int? = null,
    @SerializedName("comments_count")
    @Expose
    var commentsCount: Int? = null,
    @SerializedName("attachments_count")
    @Expose
    var attachmentsCount: Int? = null,
    @SerializedName("rebounds_count")
    @Expose
    var reboundsCount: Int? = null,
    @SerializedName("buckets_count")
    @Expose
    var bucketsCount: Int? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null,
    @SerializedName("html_url")
    @Expose
    var htmlUrl: String? = null,
    @SerializedName("attachments_url")
    @Expose
    var attachmentsUrl: String? = null,
    @SerializedName("buckets_url")
    @Expose
    var bucketsUrl: String? = null,
    @SerializedName("comments_url")
    @Expose
    var commentsUrl: String? = null,
    @SerializedName("likes_url")
    @Expose
    var likesUrl: String? = null,
    @SerializedName("projects_url")
    @Expose
    var projectsUrl: String? = null,
    @SerializedName("rebounds_url")
    @Expose
    var reboundsUrl: String? = null,
    @SerializedName("animated")
    @Expose
    var animated: Boolean? = null,
    @SerializedName("tags")
    @Expose
    var tags: List<String>? = null,
    @SerializedName("user")
    @Expose
    @Ignore
    var user: User? = null,
    @SerializedName("team")
    @Expose
    @Ignore
    var team: Team? = null
)