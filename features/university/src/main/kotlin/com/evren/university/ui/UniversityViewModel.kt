package com.evren.university.ui

import androidx.lifecycle.Transformations
import com.evren.core.base.viewmodel.BaseViewModel
import com.evren.repository.interactors.base.None
import com.evren.repository.interactors.base.handle
import com.evren.repository.interactors.university.GetUniversityInteractor
import com.evren.repository.model.entities.university.UniversityEntityItem
import com.evren.repository.shared.TokenInfoInstance
import com.evren.repository.shared.UserInfoInstance
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class UniversityViewModel @Inject constructor(
    private val getUniversityInteractor: GetUniversityInteractor,
    val userInfoInstance: UserInfoInstance
) : BaseViewModel<List<UniversityEntityItem>?>() {

    //region Properties

    //endregion

    //region Functions

    /**
     * Triggering interactor in view model scope
     */
    override suspend fun loadData() {
        getUniversityInteractor(None()).collect{
            it.handle(::handleState, ::handleFailure,::handleSuccess)
        }
    }
    //endregion
}
