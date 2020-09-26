package com.evren.authentication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.evren.authentication.R
import com.evren.core.base.viewmodel.BaseViewModel
import com.evren.core.utils.ResourceProvider
import com.evren.repository.interactors.base.handle
import com.evren.repository.interactors.login.LoginInteractor
import com.evren.repository.model.dto.PasswordTokenDto
import com.evren.repository.model.entities.token.TokenEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(
     val getUniversityId: Int,
    val resourceProvider: ResourceProvider,
    private val loginInteractor: LoginInteractor
) : BaseViewModel<TokenEntity>() {

    //region Properties

    val username = MutableLiveData<String>()

    val usernameError = MutableLiveData<String>()

    val password = MutableLiveData<String>()

    val passwordError = MutableLiveData<String>()

    fun isEmailEmpty() = username.value.isNullOrEmpty()

    fun isPasswordEmpty() =  password.value.isNullOrEmpty()

    fun login(){

        if(isEmailEmpty()){
            usernameError.value = resourceProvider.getString(R.string.username_error)
            return
        }


        if(isPasswordEmpty()){
            passwordError.value = resourceProvider.getString(R.string.password_error)
            return
        }


        viewModelScope.launch {
            loginInteractor(LoginInteractor.Params(PasswordTokenDto(username = username.value!!,password = password.value!!))).collect{
                it.handle(::handleState, ::handleFailure, ::handleSuccess)
            }
        }
    }


    //endregion

    //region Functions

    /**
     * Triggering interactor in view model scope
     */
    override suspend fun loadData() {

    }
    //endregion
}
