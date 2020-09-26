package com.evren.authentication.di

import com.evren.authentication.di.modules.AuthenticationModule
import com.evren.authentication.di.scopes.AuthenticationScope
import com.evren.authentication.ui.AuthenticationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthenticationContributor {

    //region Contributes

    @ContributesAndroidInjector(
        modules = [AuthenticationModule::class]
    )
    @AuthenticationScope
    abstract fun authenticationFragment(): AuthenticationFragment
    //endregion
}