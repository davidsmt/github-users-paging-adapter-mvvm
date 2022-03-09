package com.githubstarredusers.view.di

import com.githubstarredusers.view.screens.githubuserprofile.GitHubUserProfileFragment
import com.githubstarredusers.view.screens.githubusers.GitHubUsersFragment
import dagger.Subcomponent

@Subcomponent
@FragmentScope
interface ViewComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ViewComponent
    }

    fun inject(fragment: GitHubUsersFragment)

    fun inject(fragment: GitHubUserProfileFragment)

}

interface ViewComponentFactoryProvider {
    fun provideViewComponentFactory(): ViewComponent.Factory
}
