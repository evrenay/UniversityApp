package com.evren.repository.interactors.token

import com.evren.repository.model.entities.token.TokenEntity
import com.evren.repository.interactors.base.BaseInteractor
import com.evren.repository.interactors.base.Failure
import com.evren.repository.interactors.base.None
import com.evren.repository.interactors.base.Success
import com.evren.repository.shared.TokenInfoInstance
import com.evren.repository.sources.NetworkSource
import com.evren.repository.sources.PersistenceSource
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject
import com.evren.repository.interactors.base.Result
import com.evren.repository.shared.UserInfoInstance

class GuestLoginInteractor @Inject constructor() : BaseInteractor<TokenEntity, None>() {

    //region Properties

    @field:Inject
    internal lateinit var networkSource: NetworkSource

    @field:Inject
    internal lateinit var persistenceSource: PersistenceSource

    @field:Inject
    internal lateinit var tokenInfoInstance: TokenInfoInstance

    @field:Inject
    internal lateinit var userInfoInstance: UserInfoInstance

    //endregion

    //region Functions
    override suspend fun FlowCollector<Result<TokenEntity>>.run(params : None) {
        when(val token = persistenceSource.getToken()){
            is Success ->{
                tokenInfoInstance.tokenLiveData.postValue(token.successData)
                userInfoInstance.setLiveUserInfo()
                token.successData.refreshToken?.let {
                    when(val response = networkSource.refreshToken(refreshToken = it)){
                        is Success ->{
                            tokenInfoInstance.updateToken(response.successData)
                            emit(token)
                        }
                        is Failure ->{
                            getGuestUser()
                        }
                    }
                }
            }
            is Failure ->{
                getGuestUser()
            }
        }
    }

    private suspend fun FlowCollector<Result<TokenEntity>>.getGuestUser() {
        when(val response = networkSource.guestLogin()){
            is Success ->{
                tokenInfoInstance.saveTokenInfo(response.successData)
                emit(response)
            }
            is Failure ->{
                emit(response)
            }
        }
    }




    //endregion
}