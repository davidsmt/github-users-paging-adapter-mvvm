package com.githubstarredusers.presentation.mappers

import com.githubstarredusers.domain.models.GitHubUserProfile
import com.githubstarredusers.presentation.models.GitHubUserProfileUiModel
import org.junit.Assert.assertEquals
import org.junit.Test

class GitHubUserProfileUiMapperTest {

    @Test
    fun `when executed then it should return correct mapped ui model`() {
        val gitHubUserProfile = buildGitHubUserProfile()
        val gitHubUserProfileUiModel = buildGitHubUserProfileUiModel()
        val mappedObject = GitHubUserProfileUiMapper.map(gitHubUserProfile)

        assertEquals(gitHubUserProfileUiModel, mappedObject)
    }

    private fun buildGitHubUserProfile() = GitHubUserProfile(
        avatarUrl = "avatarUrl",
        email = "email",
        followersTotalCount = 1,
        followingTotalCount = 2,
        login = "login",
        name = "name",
        starredRepositories = emptyList(),
        topRepositories = emptyList()
    )

    private fun buildGitHubUserProfileUiModel() = GitHubUserProfileUiModel(
        avatarUrl = "avatarUrl",
        email = "email",
        followersTotalCount = "1",
        followingTotalCount = "2",
        login = "login",
        name = "name",
        starredRepositories = emptyList(),
        topRepositories = emptyList()
    )

}
