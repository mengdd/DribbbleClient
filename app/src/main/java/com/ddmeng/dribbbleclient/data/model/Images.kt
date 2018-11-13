package com.ddmeng.dribbbleclient.data.model

import android.arch.persistence.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Images")
data class Images(

    @SerializedName("hidpi")
    @Expose
    var hidpi: String? = null,
    @SerializedName("normal")
    @Expose
    var normal: String? = null,
    @SerializedName("teaser")
    @Expose
    var teaser: String? = null
)