package com.evren.repository.model.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseApiListResponse<T>(
    val data: List<T>?,
    val encrypted: Boolean?,
    val hashKey: String?,
    val messages: List<BaseMessage>?,
    val status: Int?
)