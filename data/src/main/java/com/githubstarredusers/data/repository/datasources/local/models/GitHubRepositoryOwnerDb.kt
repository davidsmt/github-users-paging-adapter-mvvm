package com.githubstarredusers.data.repository.datasources.local.models

data class GitHubRepositoryOwnerDb(
    val id: Int,
    val login: String,
    val avatarUrl: String?
)