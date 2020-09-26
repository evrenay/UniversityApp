package com.evren.repository.sources

import android.net.NetworkInfo
import com.evren.repository.model.entities.token.TokenEntity
import com.evren.repository.interactors.base.EmptyResultError
import com.evren.repository.interactors.base.Failure
import com.evren.repository.interactors.base.NetworkError
import com.evren.repository.interactors.base.Reason
import com.evren.repository.interactors.base.ResponseError
import com.evren.repository.interactors.base.Result
import com.evren.repository.interactors.base.Success
import com.evren.repository.interactors.base.TimeoutError
import com.evren.repository.model.dto.PasswordTokenDto
import com.evren.repository.model.entities.university.UniversityEntityItem
import com.evren.repository.model.entities.university.detail.UniversityDetailEntity
import com.evren.repository.network.ApiImpl
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Provider

private const val DEFAULT_IMAGE_SIZE = 480

/**
 * NetworkSource for fetching results using api and wrapping them as contracted in [repository][Repository],
 * returning either [failure][Failure] with proper [reason][Reason] or [success][Success] with data
 */
internal class NetworkSource @Inject constructor(
    private val apiImpl: ApiImpl,
    private val networkInfoProvider: Provider<NetworkInfo>
)  {

    //region Properties

    private val isNetworkConnected: Boolean
        get() {
            val networkInfo = networkInfoProvider.get()
            return networkInfo != null && networkInfo.isConnected
        }
    //endregion

    //region Functions

     suspend fun login(passwordTokenDto: PasswordTokenDto): Result<TokenEntity> =
        safeExecute({
            apiImpl.passwordLogin(username = passwordTokenDto.username,password = passwordTokenDto.password)
        }) { entity ->
            entity
        }

    suspend fun getUniversity(): Result<List<UniversityEntityItem>> =
        safeExecute({
            apiImpl.getUniversity()
        }) { entity ->
           entity.data!!.filter { universityEntityItem -> universityEntityItem.url != null  }
        }

     suspend fun guestLogin(): Result<TokenEntity> =
        safeExecute({
            apiImpl.guestLogin()
        }) { entity ->
            entity
        }


     suspend fun refreshToken(refreshToken: String): Result<TokenEntity> =
        safeExecute({
            apiImpl.refreshToken(refreshToken = refreshToken)
        }) { entity ->
            entity
        }

     suspend fun getUniversityById(id: Int): Result<UniversityDetailEntity> =
        safeExecute({
            apiImpl.getUniversityById(id)
        }) {
            it.data!!
        }

    private inline fun <T, R> safeExecute(
        block: () -> Response<T>,
        transform: (T) -> R
    ) =
        if (isNetworkConnected) {
            try {
                block().extractResponseBody(transform)
            } catch (e: IOException) {
                Failure(TimeoutError())
            }
        } else {
            Failure(NetworkError())
        }

    private inline fun <T, R> Response<T>.extractResponseBody(transform: (T) -> R) =
        if (isSuccessful) {
            body()?.let {
                Success(transform(it))
            } ?: Failure(EmptyResultError())
        } else {
            Failure(ResponseError())
        }


    //endregion
}
