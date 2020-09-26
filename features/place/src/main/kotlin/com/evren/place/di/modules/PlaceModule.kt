package com.evren.place.di.modules

import androidx.lifecycle.ViewModel
import com.evren.core.di.keys.ViewModelKey
import com.evren.place.ui.PlaceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PlaceModule {

    //region ViewModels

    @Binds
    @IntoMap
    @ViewModelKey(PlaceViewModel::class)
    abstract fun placeViewModel(placeViewModel: PlaceViewModel): ViewModel
    //endregion

    @Module
    companion object {

        /**
         * Provides
         */
    }
}
