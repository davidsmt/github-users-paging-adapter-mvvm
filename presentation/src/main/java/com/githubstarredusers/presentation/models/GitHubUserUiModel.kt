package com.githubstarredusers.presentation.models

data class GitHubUserUiModel(
    val id: Long,
    val login: String,
    val avatarUrl: String?,
    val url: String?
)