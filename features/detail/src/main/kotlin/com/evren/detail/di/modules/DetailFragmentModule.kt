package com.evren.detail.di.modules

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.evren.core.di.keys.ViewModelKey
import com.evren.detail.ui.DetailFragment
import com.evren.detail.ui.DetailFragmentArgs
import com.evren.detail.ui.DetailViewModel
import com.evren.repository.interactors.university.detail.GetUniversityDetails

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class DetailFragmentModule {

    //region ViewModels

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun detailViewModel(detailViewModel: DetailViewModel): ViewModel
    //endregion

    @Module
    companion object {

        /**
         * Provides launch detail params
         */
        @Provides
        @JvmStatic
        fun provideGetLaunchDetailParams(fragment: DetailFragment): GetUniversityDetails.Params {
            val args by fragment.navArgs<DetailFragmentArgs>()
            return GetUniversityDetails.Params(args.universityId)
        }
    }
}
