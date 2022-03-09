package com.githubstarredusers.presentation.models

data class GitHubRepositoryUiModel(
    val id: Int,
    val name: String,
    val description: String,
    val owner: GitHubRepositoryOwnerUiModel,
    val language: String,
    val stargazerCount: String
)
