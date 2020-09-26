package com.evren.place.ui

import com.evren.core.base.viewmodel.BaseViewModel
import com.evren.repository.model.entities.university.UniversityEntityItem
import com.evren.repository.shared.SharedValues
import com.evren.repository.shared.UserInfoInstance
import javax.inject.Inject

class PlaceViewModel @Inject constructor(
    val sharedValues : SharedValues,
    val userInfoInstance : UserInfoInstance
) : BaseViewModel<UniversityEntityItem>() {

    //region Properties

    //endregion

    //region Functions

    /**
     * Triggering interactor in view model scope
     */
    override suspend fun loadData() {

    }
    //endregion
}
