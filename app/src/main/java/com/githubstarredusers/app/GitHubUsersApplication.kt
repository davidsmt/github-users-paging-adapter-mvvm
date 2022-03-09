package com.githubstarredusers.app

import android.app.Application
import com.githubstarredusers.app.di.AppComponent
import com.githubstarredusers.app.di.DaggerAppComponent
import com.githubstarredusers.view.di.ViewComponent
import com.githubstarredusers.view.di.ViewComponentFactoryProvider

class GitHubUsersApplication : Application(), ViewComponentFactoryProvider {

    // DI components
    private val _appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun provideViewComponentFactory(): ViewComponent.Factory =
        _appComponent.viewComponentFactory()

}
