package com.evren.repository.model.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseApiObjectResponse<T>(
    val data: T?,
    val encrypted: Boolean?,
    val hashKey: String?,
    val messages: List<BaseMessage>?,
    val status: Int?
)