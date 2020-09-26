package com.evren.university.di.modules

import androidx.lifecycle.ViewModel
import com.evren.core.di.keys.ViewModelKey
import com.evren.university.ui.UniversityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UniversityModule {

    //region ViewModels

    @Binds
    @IntoMap
    @ViewModelKey(UniversityViewModel::class)
    abstract fun universityViewModel(universityViewModel: UniversityViewModel): ViewModel
    //endregion

    @Module
    companion object {

        /**
         * Provides
         */
    }
}