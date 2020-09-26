package com.evren.repository.interactors.login

import com.evren.repository.interactors.base.BaseInteractor
import com.evren.repository.interactors.base.Failure
import com.evren.repository.interactors.base.InteractorParameters
import com.evren.repository.interactors.base.Success
import com.evren.repository.model.dto.PasswordTokenDto
import com.evren.repository.model.entities.token.TokenEntity
import com.evren.repository.shared.TokenInfoInstance
import com.evren.repository.sources.NetworkSource
import com.evren.repository.interactors.base.Result
import com.evren.repository.model.entities.user.UserEntity
import com.evren.repository.shared.UserInfoInstance
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class LoginInteractor @Inject constructor() : BaseInteractor<TokenEntity, LoginInteractor.Params>() {


    //region Properties

    @field:Inject
    internal lateinit var networkSource: NetworkSource

    @field:Inject
    internal lateinit var tokenInfoInstance: TokenInfoInstance

    @field:Inject
    internal lateinit var userInfoInstance: UserInfoInstance

    //endregion

    //region Functions
    override suspend fun FlowCollector<Result<TokenEntity>>.run(params: Params) {
        when(val response = networkSource.login(params.passwordTokenDto)){
            is Success ->{
                userInfoInstance.saveUserInfo(UserEntity(username = params.passwordTokenDto.username,password = params.passwordTokenDto.password))
                tokenInfoInstance.saveTokenInfo(response.successData)
                emit(response)
            }
            is Failure ->{
                emit(response)
            }
        }
    }
    //endregion


    data class Params(
        val passwordTokenDto: PasswordTokenDto
    ) : InteractorParameters


}