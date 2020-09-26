package com.evren.authentication.di.modules

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.evren.authentication.ui.AuthenticationFragment
import com.evren.authentication.ui.AuthenticationFragmentArgs
import com.evren.authentication.ui.AuthenticationViewModel
import com.evren.core.di.keys.ViewModelKey
import com.evren.repository.interactors.university.detail.GetUniversityDetails
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class AuthenticationModule {

    //region ViewModels

    @Binds
    @IntoMap
    @ViewModelKey(AuthenticationViewModel::class)
    abstract fun authenticationViewModel(authenticationViewModel: AuthenticationViewModel): ViewModel
    //endregion

    @Module
    companion object {

        /**
         * Provides
         */

        @Provides
        @JvmStatic
        fun provideGetLaunchDetailParams(fragment: AuthenticationFragment): Int {
            val args by fragment.navArgs<AuthenticationFragmentArgs>()
            return args.universityId
        }
    }
}