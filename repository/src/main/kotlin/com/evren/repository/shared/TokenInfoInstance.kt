package com.evren.repository.shared

import androidx.lifecycle.MutableLiveData
import com.evren.repository.model.entities.token.TokenEntity
import com.evren.repository.sources.PersistenceSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInfoInstance @Inject constructor()  {

    @field:Inject
    internal lateinit var persistenceSource: PersistenceSource

    val tokenLiveData = MutableLiveData<TokenEntity>()


    suspend fun updateToken(tokenEntity: TokenEntity){
        tokenLiveData.postValue(tokenEntity)
        tokenLiveData.value?.let {
            persistenceSource.saveToken(it)
        }

    }


    fun updateTokenSync(tokenEntity: TokenEntity){
        tokenLiveData.value = tokenEntity
        tokenLiveData.value?.let {
            persistenceSource.saveTokenSync(it)
        }

    }

    suspend fun saveTokenInfo(tokenEntity: TokenEntity){
        persistenceSource.saveToken(tokenEntity)
        tokenLiveData.postValue(tokenEntity)
    }

    fun getRefreshToken() : String? = tokenLiveData.value?.refreshToken

    fun getToken() : String? = tokenLiveData.value?.token

    fun getTokenType(): String?= tokenLiveData.value?.tokenType
}