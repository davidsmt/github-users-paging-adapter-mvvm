package com.githubstarredusers.data.repository.datasources.local.mappers

import com.githubstarredusers.data.repository.datasources.local.models.GitHubUserProfileDb
import com.githubstarredusers.domain.models.GitHubUserProfile

object GitHubUserProfileDbMapper {

    fun mapToDomain(dbModel: GitHubUserProfileDb) =
        GitHubUserProfile(
            login = dbModel.login,
            email = dbModel.email,
            followersTotalCount = dbModel.followersTotalCount,
            followingTotalCount = dbModel.followingTotalCount,
            avatarUrl = dbModel.avatarUrl,
            name = dbModel.name.orEmpty(),
            starredRepositories = GitHubRepositoryDbMapper.mapToDomain(dbModel.starredRepositories)
                .orEmpty(),
            topRepositories = GitHubRepositoryDbMapper.mapToDomain(dbModel.topRepositories)
                .orEmpty()
        )

    fun mapToDb(domainModel: GitHubUserProfile) =
        GitHubUserProfileDb(
            login = domainModel.login,
            email = domainModel.email,
            followersTotalCount = domainModel.followersTotalCount,
            followingTotalCount = domainModel.followingTotalCount,
            avatarUrl = domainModel.avatarUrl,
            name = domainModel.name,
            starredRepositories = GitHubRepositoryDbMapper.mapToDb(domainModel.starredRepositories),
            topRepositories = GitHubRepositoryDbMapper.mapToDb(domainModel.topRepositories)
        )
}