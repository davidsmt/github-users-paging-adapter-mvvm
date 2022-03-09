package com.githubstarredusers.app.di

import android.content.Context
import com.githubstarredusers.data.di.NetworkModule
import com.githubstarredusers.data.di.RepositoryModule
import com.githubstarredusers.presentation.di.CoroutineModule
import com.githubstarredusers.presentation.di.PresentationViewModelModule
import com.githubstarredusers.view.di.ViewComponent
import com.githubstarredusers.view.di.ViewModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        CoroutineModule::class,
        PresentationViewModelModule::class,
        ViewModule::class
    ]
)
@ApplicationScope
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun viewComponentFactory(): ViewComponent.Factory

}