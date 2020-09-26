package com.evren.core.di

import android.app.Application
import android.content.Context
import android.net.NetworkInfo
import com.evren.core.utils.ResourceProvider
import com.evren.repository.network.TokenAuthenticator
import com.evren.repository.shared.SharedValues
import com.evren.repository.shared.TokenInfoInstance
import com.evren.repository.shared.UserInfoInstance
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class])
interface CoreComponent {

    fun getAppContext(): Context

    fun getNetworkInfo(): NetworkInfo?

    fun getTokenInfoInstance() : TokenInfoInstance

    fun getTokenAuthenticator() : TokenAuthenticator

    fun getUserInfoInstance() : UserInfoInstance

    fun getResourceProvider() : ResourceProvider

    fun getSharedValue()      : SharedValues

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): CoreComponent
    }
}
