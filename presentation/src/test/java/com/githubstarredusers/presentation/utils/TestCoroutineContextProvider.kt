package com.githubstarredusers.presentation.utils

import com.githubstarredusers.presentation.coroutines.CoroutineContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineContextProvider : CoroutineContextProvider {

    override fun main(): CoroutineDispatcher = Dispatchers.Unconfined

    override fun io(): CoroutineDispatcher = Dispatchers.Unconfined

}
