package com.githubstarredusers.domain.models

data class GitHubUser(
    val id: Long,
    val login: String,
    val avatarUrl: String?,
    val url: String?
)