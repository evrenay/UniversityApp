package com.evren.detail.ui

import androidx.lifecycle.Transformations.map
import com.evren.core.base.viewmodel.BaseViewModel
import com.evren.repository.interactors.university.detail.GetUniversityDetails
import com.evren.repository.interactors.base.handle
import com.evren.repository.model.entities.university.detail.UniversityDetailEntity
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getUniversityDetails: GetUniversityDetails,
    private val getUniversityDetailsParams: GetUniversityDetails.Params
) : BaseViewModel<UniversityDetailEntity>() {

    //region Properties

    val name = map(successData) {
        if (it.name.isNullOrEmpty()) " " else it.name
    }

    val description = map(successData) {
        if (it.detail.isNullOrEmpty()) " " else it.detail
    }

    val type = map(successData) {
        if (it.type.isNullOrEmpty()) " " else it.type
    }
    //endregion

    //region Functions

    /**
     * Triggering interactor in view model scope
     */
    override suspend fun loadData() {
        getUniversityDetails(getUniversityDetailsParams).collect {
            it.handle(::handleState, ::handleFailure, ::handleSuccess)
        }
    }
    //endregion
}
