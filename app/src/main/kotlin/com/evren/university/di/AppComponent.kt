package com.evren.university.di

import com.evren.core.di.CoreComponent
import com.evren.university.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class
    ],

    dependencies = [CoreComponent::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(component: CoreComponent): AppComponent
    }
}
