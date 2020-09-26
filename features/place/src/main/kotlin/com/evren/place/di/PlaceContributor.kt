package com.evren.place.di

import com.evren.place.di.modules.PlaceModule
import com.evren.place.di.scopes.PlaceScope
import com.evren.place.ui.PlaceFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Contributes fragments & view models in this module
 */
@Module
abstract class PlaceContributor {

    //region Contributes

    @ContributesAndroidInjector(
        modules = [PlaceModule::class]
    )
    @PlaceScope
    abstract fun placeFragment(): PlaceFragment
    //endregion
}