package com.githubstarredusers.domain.models

data class GitHubUserProfile(
    val avatarUrl: String?,
    val email: String?,
    val followersTotalCount: Int,
    val followingTotalCount: Int,
    val login: String,
    val name: String,
    val starredRepositories: List<GitHubRepository>,
    val topRepositories: List<GitHubRepository>
)