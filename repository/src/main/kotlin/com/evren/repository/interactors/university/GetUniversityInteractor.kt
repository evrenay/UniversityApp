package com.evren.repository.interactors.university

import com.evren.repository.interactors.base.*
import com.evren.repository.model.entities.university.UniversityEntityItem
import com.evren.repository.shared.SharedValues
import com.evren.repository.sources.NetworkSource
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetUniversityInteractor @Inject constructor() : BaseInteractor<List<UniversityEntityItem>, None>()  {

    @field:Inject
    internal lateinit var networkSource: NetworkSource

    @field:Inject
    internal lateinit var sharedValues: SharedValues


    override suspend fun FlowCollector<Result<List<UniversityEntityItem>>>.run(params: None) {
        val res = networkSource.getUniversity()
        when(res){
            is Success->{
                sharedValues.sharedUniversity.postValue(res.successData)
                emit(res)
            }
            is Failure ->{
                emit(res)
            }
        }

    }
}