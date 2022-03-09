package com.githubstarredusers.presentation.di

import com.githubstarredusers.presentation.coroutines.CoroutineContextProvider
import com.githubstarredusers.presentation.coroutines.DefaultCoroutineContextProvider
import dagger.Module
import dagger.Provides

@Module
class CoroutineModule {

    @Provides
    fun provideCoroutineContextProvider(): CoroutineContextProvider {
        return DefaultCoroutineContextProvider()
    }

}