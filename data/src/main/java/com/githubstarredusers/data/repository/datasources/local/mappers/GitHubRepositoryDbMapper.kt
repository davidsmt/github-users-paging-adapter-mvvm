package com.githubstarredusers.data.repository.datasources.local.mappers

import com.githubstarredusers.data.repository.datasources.local.models.GitHubRepositoryDb
import com.githubstarredusers.domain.models.GitHubRepository

object GitHubRepositoryDbMapper {

    fun mapToDomain(dbModels: List<GitHubRepositoryDb>?) = dbModels?.map { mapToDomain(it) }

    private fun mapToDomain(dbModel: GitHubRepositoryDb) =
        GitHubRepository(
            id = dbModel.id,
            name = dbModel.name,
            description = dbModel.description,
            owner = GitHubRepositoryOwnerDbMapper.mapToDomain(dbModel.owner),
            language = dbModel.language,
            stargazerCount = dbModel.stargazerCount
        )

    fun mapToDb(domainModels: List<GitHubRepository>) = domainModels.map { mapToDb(it) }

    private fun mapToDb(domainModel: GitHubRepository) =
        GitHubRepositoryDb(
            id = domainModel.id,
            name = domainModel.name,
            description = domainModel.description,
            owner = GitHubRepositoryOwnerDbMapper.mapToDb(domainModel.owner),
            language = domainModel.language,
            stargazerCount = domainModel.stargazerCount
        )

}