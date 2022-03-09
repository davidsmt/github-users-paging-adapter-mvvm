package com.githubstarredusers.data.repository.datasources.remote

import com.githubstarredusers.data.network.GitHubApi
import com.githubstarredusers.data.repository.datasources.remote.mappers.GitHubApiMapper
import com.githubstarredusers.domain.models.GitHubUser
import com.githubstarredusers.domain.models.GitHubUserProfile
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class RemoteDataSource(private val gitHubApi: GitHubApi) {

    suspend fun loadUsers(page: Int, pageSize: Int): List<GitHubUser> {
        return gitHubApi.getUsers(page, pageSize).items?.mapNotNull {
            GitHubApiMapper.mapToDomain(it)
        } ?: emptyList()
    }

    suspend fun loadUserProfile(login: String): GitHubUserProfile {
        val profile = gitHubApi.getUserProfile(login)

        val topRepositories = coroutineScope {
            async { gitHubApi.getRepositories(login) }
        }

        val starredRepositories = coroutineScope {
            async { gitHubApi.getStarred(login) }
        }

        return GitHubApiMapper.mapToDomain(
            apiModel = profile,
            topRepositories = topRepositories.await(),
            starredRepositories = starredRepositories.await()
        )
    }
}