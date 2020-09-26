package com.evren.core.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.evren.core.utils.ResourceProvider
import dagger.Module
import dagger.Provides

@Module
class CoreModule {

    @Provides
    fun proivdeAppContext(app: Application): Context = app.applicationContext

    @Provides
    fun provideNetworkInfo(app: Application): NetworkInfo? =
        (app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

    @Provides
    fun provideResourceProvider (context: Context) = ResourceProvider(context)
}
