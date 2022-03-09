package com.githubstarredusers.presentation.mappers

import com.githubstarredusers.domain.models.GitHubRepository
import com.githubstarredusers.domain.models.GitHubRepositoryOwner
import com.githubstarredusers.domain.models.GitHubUserProfile
import com.githubstarredusers.presentation.models.GitHubRepositoryOwnerUiModel
import com.githubstarredusers.presentation.models.GitHubRepositoryUiModel
import com.githubstarredusers.presentation.models.GitHubUserProfileUiModel

object GitHubUserProfileUiMapper {

    fun map(profile: GitHubUserProfile): GitHubUserProfileUiModel =
        GitHubUserProfileUiModel(
            avatarUrl = profile.avatarUrl,
            email = profile.email,
            followersTotalCount = profile.followersTotalCount.toString(),
            followingTotalCount = profile.followingTotalCount.toString(),
            login = profile.login,
            name = profile.name,
            starredRepositories = mapRepositories(profile.starredRepositories),
            topRepositories = mapRepositories(profile.topRepositories),
        )

    private fun mapRepositories(repositories: List<GitHubRepository>) =
        repositories.map {
            GitHubRepositoryUiModel(
                id = it.id,
                name = it.name,
                description = it.description,
                owner = mapRepositoryOwner(it.owner),
                language = it.language,
                stargazerCount = it.stargazerCount.toString()
            )
        }

    private fun mapRepositoryOwner(owner: GitHubRepositoryOwner): GitHubRepositoryOwnerUiModel =
        GitHubRepositoryOwnerUiModel(
            id = owner.id,
            login = owner.login,
            avatarUrl = owner.avatarUrl
        )

}