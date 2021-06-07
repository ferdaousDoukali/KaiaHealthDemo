package com.kaiahealth.demo.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Exercise(
    val id: Int,
    val name: String,
    @SerializedName("cover_image_url")
    val coverImageUrl: String,
    @SerializedName("video_url")
    val videoUrl: String
): Serializable
