package com.evren.repository.model.entities.university.detail

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UniversityDetailEntity(
    val detail: String?,
    val id: Int?,
    val images: List<Image>?,
    val lat: Double?,
    val lng: Double?,
    val majorDetail: List<MajorDetail>?,
    val name: String?,
    val shortDetail: String?,
    val type: String?,
    val url: String?
)