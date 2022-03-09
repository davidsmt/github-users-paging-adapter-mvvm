package com.githubstarredusers.presentation.mappers

import com.githubstarredusers.domain.models.GitHubUser
import com.githubstarredusers.presentation.models.GitHubUserUiModel

object GitHubUsersUiMapper {

    fun map(users: List<GitHubUser>) = users.map {
        map(it)
    }

    fun map(user: GitHubUser): GitHubUserUiModel =
        GitHubUserUiModel(
            id = user.id,
            login = user.login,
            avatarUrl = user.avatarUrl,
            url = user.url
        )
}