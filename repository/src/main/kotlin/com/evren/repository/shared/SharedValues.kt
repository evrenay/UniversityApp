package com.evren.repository.shared

import androidx.lifecycle.MutableLiveData
import com.evren.repository.model.entities.university.UniversityEntityItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedValues @Inject constructor() {
    val sharedUniversity = MutableLiveData<List<UniversityEntityItem>?>()
}