package com.evren.repository.network

import com.evren.repository.BuildConfig.BASE_URL
import com.evren.repository.model.entities.BaseApiListResponse
import com.evren.repository.model.entities.BaseApiObjectResponse
import com.evren.repository.model.entities.token.TokenEntity
import com.evren.repository.model.entities.university.UniversityEntityItem
import com.evren.repository.model.entities.university.detail.UniversityDetailEntity
import com.evren.repository.shared.TokenInfoInstance
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal const val TIMEOUT_DURATION = 7L

internal class ApiImpl @Inject constructor(
    val tokenInfoInstance: TokenInfoInstance,
    val tokenAuthenticator: TokenAuthenticator
) : Api {

    //region Properties

    private val service by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                    .addInterceptor {chain->
                        val newBuilder = chain.request().newBuilder()

                        newBuilder.addHeader("Authorization",tokenInfoInstance.getTokenType()+" "+tokenInfoInstance.getToken())
                        chain.proceed(newBuilder.build())
                    }
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .authenticator(tokenAuthenticator).build()
            )
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
            .create(Api::class.java)
    }
    //endregion

    //region Functions

    override suspend fun guestLogin(
        grantType: String,
        clientId: String,
        clientSecret: String
    ): Response<TokenEntity> = service.guestLogin()

    override suspend fun passwordLogin(
        grantType: String,
        clientId: String,
        clientSecret: String,
        username: String,
        password: String
    ): Response<TokenEntity> = service.passwordLogin(username = username,password = password)


    override suspend fun refreshToken(
        grantType: String,
        clientId: String,
        clientSecret: String,
        refreshToken: String?
    ): Response<TokenEntity> = service.refreshToken(refreshToken = refreshToken)

    override fun refreshTokenSync(
        grantType: String,
        clientId: String,
        clientSecret: String,
        refreshToken: String?
    ): Call<Response<TokenEntity>> = service.refreshTokenSync(refreshToken = refreshToken)

    override suspend fun getUniversity(): Response<BaseApiListResponse<UniversityEntityItem>> = service.getUniversity()



    override suspend fun getUniversityById(
        id: Int
    ): Response<BaseApiObjectResponse<UniversityDetailEntity>> =
        service.getUniversityById(id)
    //endregion
}
