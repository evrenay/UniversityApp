package com.evren.repository.network

import com.evren.repository.BuildConfig
import com.evren.repository.model.entities.BaseApiListResponse
import com.evren.repository.model.entities.BaseApiObjectResponse
import com.evren.repository.model.entities.token.TokenEntity
import com.evren.repository.model.entities.university.UniversityEntityItem
import com.evren.repository.model.entities.university.detail.UniversityDetailEntity
import com.evren.repository.model.enum.GrandType
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit interface for networking
 */
internal interface Api {




    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun guestLogin(
        @Field("grant_type")
        grantType : String = GrandType.GLOBAL_TOKEN.type,
        @Field("client_id")
        clientId : String = BuildConfig.CLIENT_ID,
        @Field("client_secret")
        clientSecret : String = BuildConfig.CLIENT_SECRET
    ): Response<TokenEntity>

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun passwordLogin(
        @Field("grant_type")
        grantType : String = GrandType.PASSWORD_TOKEN.type,
        @Field("client_id")
        clientId : String = BuildConfig.CLIENT_ID,
        @Field("client_secret")
        clientSecret : String = BuildConfig.CLIENT_SECRET,
        @Field("username")
        username : String,
        @Field("password")
        password : String
    ): Response<TokenEntity>

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun refreshToken(
        @Field("grant_type")
        grantType : String = GrandType.REFRESH_TOKEN.type,
        @Field("client_id")
        clientId : String = BuildConfig.CLIENT_ID,
        @Field("client_secret")
        clientSecret : String = BuildConfig.CLIENT_SECRET,
        @Field("refresh_token")
        refreshToken : String?
    ): Response<TokenEntity>


    @FormUrlEncoded
    @POST("oauth/token")
    fun refreshTokenSync(
        @Field("grant_type")
        grantType : String = GrandType.REFRESH_TOKEN.type,
        @Field("client_id")
        clientId : String = BuildConfig.CLIENT_ID,
        @Field("client_secret")
        clientSecret : String = BuildConfig.CLIENT_SECRET,
        @Field("refresh_token")
        refreshToken : String?
    ): Call<Response<TokenEntity>>

    //region Get

    @GET("university")
    suspend fun getUniversity(
    ): Response<BaseApiListResponse<UniversityEntityItem>>


    @GET("university/{uniId}")
    suspend fun getUniversityById(
        @Path("uniId") id: Int
    ): Response<BaseApiObjectResponse<UniversityDetailEntity>>
    //endregion
}
