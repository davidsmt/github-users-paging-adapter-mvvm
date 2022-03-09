package com.githubstarredusers.data.repository.datasources.remote.mappers

import com.githubstarredusers.data.repository.datasources.remote.models.GitHubUserProfileResponse
import com.githubstarredusers.data.repository.datasources.remote.models.GitHubUserResponse
import com.githubstarredusers.data.repository.datasources.remote.models.GithubRepositoryResponse
import com.githubstarredusers.domain.models.GitHubRepository
import com.githubstarredusers.domain.models.GitHubRepositoryOwner
import com.githubstarredusers.domain.models.GitHubUser
import com.githubstarredusers.domain.models.GitHubUserProfile

object GitHubApiMapper {

    fun mapToDomain(apiModel: GitHubUserResponse): GitHubUser? {
        return try {
            GitHubUser(
                id = requireNotNull(apiModel.id, { "id is required" }),
                login = requireNotNull(apiModel.login, { "login is required" }),
                avatarUrl = apiModel.avatarUrl,
                url = apiModel.url
            )
        } catch (ex: NullPointerException) {
            null
        }
    }

    fun mapToDomain(
        apiModel: GitHubUserProfileResponse,
        starredRepositories: List<GithubRepositoryResponse>,
        topRepositories: List<GithubRepositoryResponse>
    ): GitHubUserProfile {
        return GitHubUserProfile(
            avatarUrl = apiModel.avatarUrl,
            email = apiModel.email,
            followersTotalCount = apiModel.followers,
            followingTotalCount = apiModel.following,
            login = requireNotNull(apiModel.login, { "login is required" }),
            name = apiModel.name,
            starredRepositories = starredRepositories.map { mapToDomain(it) },
            topRepositories = topRepositories.map { mapToDomain(it) }
        )
    }

    private fun mapToDomain(apiModel: GithubRepositoryResponse): GitHubRepository {
        return GitHubRepository(
            id = apiModel.id,
            name = apiModel.name,
            description = apiModel.description ?: "",
            owner = apiModel.owner.let { owner ->
                GitHubRepositoryOwner(
                    id = owner.id,
                    login = owner.login,
                    avatarUrl = owner.avatarUrl
                )
            },
            language = apiModel.language ?: "Unknown",
            stargazerCount = apiModel.stargazersCount ?: 0
        )
    }

}