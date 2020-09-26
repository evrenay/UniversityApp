package com.evren.repository.shared

import androidx.lifecycle.MutableLiveData
import com.evren.repository.interactors.base.Success
import com.evren.repository.model.entities.token.TokenEntity
import com.evren.repository.model.entities.user.UserEntity
import com.evren.repository.sources.PersistenceSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInfoInstance @Inject constructor()  {

    @field:Inject
    internal lateinit var persistenceSource: PersistenceSource

    val userLiveData = MutableLiveData<UserEntity>()

    suspend fun setLiveUserInfo(){
        val res = persistenceSource.getUser()
        when(res){
            is Success ->{
                userLiveData.postValue(res.successData)
            }
        }
    }

    suspend fun saveUserInfo(userEntity: UserEntity){
        persistenceSource.saveUser(userEntity)
        userLiveData.postValue(userEntity)
    }

    fun isLoginUser() : Boolean = userLiveData.value?.username != null

}