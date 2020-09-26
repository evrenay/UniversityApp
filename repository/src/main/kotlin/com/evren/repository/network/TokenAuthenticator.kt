package com.evren.repository.network


import com.evren.repository.BuildConfig.*
import com.evren.repository.model.entities.token.TokenEntity
import com.evren.repository.model.enum.GrandType
import com.evren.repository.shared.TokenInfoInstance
import com.evren.repository.sources.PersistenceSource
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenAuthenticator
@Inject constructor(
    val tokenInfoInstance: TokenInfoInstance,
    val persistenceSource: PersistenceSource
) : Authenticator {

    var client : OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        var requestAvailable: Request? = null
        try {
            synchronized(client){
                val refreshToken = tokenInfoInstance.getRefreshToken()
                val token = tokenInfoInstance.getToken()

                if (!refreshToken.isNullOrEmpty() && !token.isNullOrEmpty() && !response.isSuccessful) {

                    val asyncResult = refreshToken(refreshToken)
                    val newRequestBuilder = response.request.newBuilder()
                    asyncResult?.let {
                            tokenInfoInstance.updateTokenSync(it)

                        addHeaders(newRequestBuilder, tokenInfoInstance.getToken(),tokenInfoInstance.getTokenType())
                    } ?: run {

                        return requestAvailable
                    }

                    requestAvailable = newRequestBuilder.build()
                    return requestAvailable
                }
            }

        } catch (ex: Exception) {
            Timber.e(ex)
        }
        return requestAvailable
    }

    fun refreshToken(refreshToken: String): TokenEntity? {
        val formBody = FormBody.Builder().
            add("grant_type", GrandType.REFRESH_TOKEN.type).
            add("client_id", CLIENT_ID)
            .add("client_secret", CLIENT_SECRET)
            .add("refresh_token",refreshToken).build()

        val request =
            Request.Builder()
                .header("Content-type", "application/x-www-form-urlencoded")
                .header("Authorization", tokenInfoInstance.getTokenType()+" "+tokenInfoInstance.getToken())
                .url(BASE_URL+"oauth/token")
                .post(formBody)
                .build()

        val response = client.newCall(request).execute()
        if (response.code == 400)
            return null
        val adapter = moshi.adapter(TokenEntity::class.java)
        return adapter.fromJson(response.body.toString())
    }

    companion object {
        val TAG = TokenAuthenticator::class.java.simpleName
        private const val AUTHORIZATION = "Authorization"

        fun addHeaders(authorisedRequest: Request.Builder, token : String?,tokenType: String?): Request.Builder {
            if (!token.isNullOrEmpty() && !tokenType.isNullOrEmpty()) {
                authorisedRequest.header(AUTHORIZATION, tokenType+" "+token )
            }
            return authorisedRequest
        }
    }
}