package com.evren.repository.model.entities.token

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "TokenEntity")
@JsonClass(generateAdapter = true)
data class TokenEntity (
    @PrimaryKey val id : Long = 1L,
    @Json(name = "access_token")
    var token : String?=null,
    @Json(name = "refresh_token")
    var refreshToken : String?= null,
    @Json(name = "token_type")
    var tokenType : String?=null,
    @Json(name = "expires_in")
    var expireIn : Int?=null,
    @Json(name = "scope")
    var scope : String?=null,
    @Json(name = "jti")
    var jti : String?=null

)