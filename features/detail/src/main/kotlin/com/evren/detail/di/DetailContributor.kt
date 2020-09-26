package com.evren.detail.di

import com.evren.detail.di.modules.DetailFragmentModule
import com.evren.detail.di.scopes.DetailScope
import com.evren.detail.ui.DetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Contributes fragments & view models in this module
 */
@Module
abstract class DetailContributor {

    //region Contributes

    @ContributesAndroidInjector(
        modules = [DetailFragmentModule::class]
    )
    @DetailScope
    abstract fun detailFragment(): DetailFragment
    //endregion
}
