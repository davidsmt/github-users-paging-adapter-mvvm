package com.githubstarredusers.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.githubstarredusers.presentation.factory.ViewModelFactory
import com.githubstarredusers.presentation.viewmodels.GitHubUserProfileViewModel
import com.githubstarredusers.presentation.viewmodels.GitHubUsersViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class PresentationViewModelModule {

    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Binds
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GitHubUserProfileViewModel::class)
    abstract fun bindGitHubUserProfileViewModel(viewModel: GitHubUserProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GitHubUsersViewModel::class)
    abstract fun bindGitHubUsersViewModel(viewModel: GitHubUsersViewModel): ViewModel

}