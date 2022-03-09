package com.githubstarredusers.presentation.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineContextProvider {

    fun main(): CoroutineDispatcher

    fun io(): CoroutineDispatcher

}
