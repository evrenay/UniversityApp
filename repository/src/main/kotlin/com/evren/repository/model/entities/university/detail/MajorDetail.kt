package com.evren.repository.model.entities.university.detail

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MajorDetail(
    val id: Int?,
    val name: String?,
    val schoolType: String?
)