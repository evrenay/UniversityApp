package com.evren.repository.model.dto

import com.evren.repository.BuildConfig
import com.evren.repository.model.enum.GrandType
import com.squareup.moshi.Json

data class PasswordTokenDto(
    @Json(name = "grant_type")
    val grantType : String = GrandType.PASSWORD_TOKEN.type,
    @Json(name = "client_id")
    val clientId : String = BuildConfig.CLIENT_ID,
    @Json(name = "client_secret")
    val clientSecret : String = BuildConfig.CLIENT_SECRET,
    @Json(name = "username")
    val username : String,
    @Json(name = "password")
    val password :  String



)