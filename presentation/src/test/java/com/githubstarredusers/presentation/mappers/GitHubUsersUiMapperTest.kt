package com.githubstarredusers.presentation.mappers

import com.githubstarredusers.domain.models.GitHubUser
import com.githubstarredusers.presentation.models.GitHubUserUiModel
import org.junit.Assert.assertEquals
import org.junit.Test

class GitHubUsersUiMapperTest {

    @Test
    fun `when executed then it should return correct mapped ui model`() {
        val gitHubUser = buildGitHubUser()
        val gitHubUserUiModel = buildGitHubUserUiModel()
        val mappedObject = GitHubUsersUiMapper.map(gitHubUser)

        assertEquals(gitHubUserUiModel, mappedObject)
    }

    private fun buildGitHubUser() = GitHubUser(
        id = 1L,
        login = "login",
        avatarUrl = "avatarUrl",
        url = null
    )

    private fun buildGitHubUserUiModel() = GitHubUserUiModel(
        id = 1L,
        login = "login",
        avatarUrl = "avatarUrl",
        url = null
    )

}
