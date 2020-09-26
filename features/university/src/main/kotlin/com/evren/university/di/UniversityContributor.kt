package com.evren.university.di

import com.evren.university.di.modules.UniversityModule
import com.evren.university.di.scopes.UniversityScope
import com.evren.university.ui.UniversityFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Contributes fragments & view models in this module
 */
@Module
abstract class UniversityContributor {

    //region Contributes

    @ContributesAndroidInjector(
        modules = [UniversityModule::class]
    )
    @UniversityScope
    abstract fun universityFragment(): UniversityFragment
    //endregion
}