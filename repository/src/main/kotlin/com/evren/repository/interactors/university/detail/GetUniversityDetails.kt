package com.evren.repository.interactors.university.detail


import com.evren.repository.interactors.base.BaseInteractor
import com.evren.repository.interactors.base.InteractorParameters
import com.evren.repository.interactors.base.Result
import com.evren.repository.model.entities.university.detail.UniversityDetailEntity
import com.evren.repository.sources.NetworkSource
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

/**
 * Gets next given number of launches
 */

class GetUniversityDetails @Inject constructor() : BaseInteractor<UniversityDetailEntity, GetUniversityDetails.Params>() {

    //region Properties

    @field:Inject
    internal lateinit var networkSource: NetworkSource

    //endregion

    //region Functions

    override suspend fun FlowCollector<Result<UniversityDetailEntity>>.run(params: Params) {


           val response = networkSource.getUniversityById(params.id)
            emit(response)

    }
    //endregion

    data class Params(
        val id: Int
    ) : InteractorParameters
}
