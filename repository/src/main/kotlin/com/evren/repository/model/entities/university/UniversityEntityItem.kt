package com.evren.repository.model.entities.university

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UniversityEntityItem(
    val id: Int?,
    val lat: Double?,
    val lng: Double?,
    val name: String?,
    val shortDetail: String?,
    val type: String?,
    val url: String?
)