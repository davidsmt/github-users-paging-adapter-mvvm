package com.githubstarredusers.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.githubstarredusers.data.network.networkBoundResource
import com.githubstarredusers.data.repository.datasources.local.LocalDataSource
import com.githubstarredusers.data.repository.datasources.remote.PagingDataSource
import com.githubstarredusers.data.repository.datasources.remote.RemoteDataSource
import com.githubstarredusers.domain.Repository
import com.githubstarredusers.domain.models.GitHubUser
import com.githubstarredusers.domain.models.GitHubUserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : Repository {

    override suspend fun loadUsers(): Flow<PagingData<GitHubUser>> {
        return Pager(PagingConfig(PagingDataSource.PAGE_SIZE)) {
            PagingDataSource(remoteDataSource = remoteDataSource)
        }.flow
    }

    override suspend fun loadUserProfile(
        login: String,
        forceRefresh: Boolean
    ): Flow<GitHubUserProfile> {
        return networkBoundResource(
            query = {
                when (val dbUser = localDataSource.loadUser(login)) {
                    null -> flowOf()
                    else -> flowOf(dbUser)
                }
            },
            fetch = {
                remoteDataSource.loadUserProfile(login)
            },
            saveFetchResult = {
                localDataSource.updateUserProfile(it)
            },
            shouldFetch = {
                it == null || forceRefresh
            }
        )
    }

}