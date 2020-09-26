package com.evren.repository.model.entities.university.detail

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val imageType: String?,
    val url: String?
)