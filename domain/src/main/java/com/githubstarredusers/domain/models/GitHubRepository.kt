package com.githubstarredusers.domain.models

data class GitHubRepository(
    val id: Int,
    val name: String,
    val description: String,
    val owner: GitHubRepositoryOwner,
    val language: String,
    val stargazerCount: Int
)