package com.evren.university.di

import androidx.lifecycle.ViewModel
import com.evren.authentication.di.AuthenticationContributor
import com.evren.core.di.keys.ViewModelKey
import com.evren.place.di.PlaceContributor
import com.evren.detail.di.DetailContributor
import com.evren.university.MainActivity
import com.evren.university.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AppModule {


    @ContributesAndroidInjector(
        modules = [
            DetailContributor::class,
            UniversityContributor::class,
            PlaceContributor::class,
            AuthenticationContributor::class]
    )
    abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Module
    companion object {

    }
}
