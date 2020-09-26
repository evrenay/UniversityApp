package com.evren.repository.model.entities.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "UserEntity")
@JsonClass(generateAdapter = true)
data class UserEntity (
    @PrimaryKey val id : Long = 1L,
    var username : String?=null,
    var password : String?= null

)