package com.githubstarredusers.data.repository.datasources.local.mappers

import com.githubstarredusers.data.repository.datasources.local.models.GitHubRepositoryOwnerDb
import com.githubstarredusers.domain.models.GitHubRepositoryOwner

object GitHubRepositoryOwnerDbMapper {

    fun mapToDomain(dbModel: GitHubRepositoryOwnerDb) =
        GitHubRepositoryOwner(
            id = dbModel.id,
            login = dbModel.login,
            avatarUrl = dbModel.avatarUrl
        )

    fun mapToDb(domainModel: GitHubRepositoryOwner) =
        GitHubRepositoryOwnerDb(
            id = domainModel.id,
            login = domainModel.login,
            avatarUrl = domainModel.avatarUrl
        )

}