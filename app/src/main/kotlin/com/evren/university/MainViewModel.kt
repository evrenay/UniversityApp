package com.evren.university

import com.evren.core.base.viewmodel.BaseViewModel
import com.evren.repository.model.entities.token.TokenEntity
import com.evren.repository.interactors.base.None
import com.evren.repository.interactors.base.handle
import com.evren.repository.interactors.token.GuestLoginInteractor
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val guestLoginInteractor: GuestLoginInteractor
) : BaseViewModel<TokenEntity>() {



    override suspend fun loadData() {
        guestLoginInteractor(None()).collect{
            it.handle(::handleState, ::handleFailure,::handleSuccess)
        }

    }





}