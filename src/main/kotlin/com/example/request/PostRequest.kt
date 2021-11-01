package com.example.request

import com.google.gson.annotations.SerializedName

data class PostRequest(
    @SerializedName("caption")
    val caption: String,
    @SerializedName("imageUrl")
    val imageUrl: String = "imageUrl",
)
