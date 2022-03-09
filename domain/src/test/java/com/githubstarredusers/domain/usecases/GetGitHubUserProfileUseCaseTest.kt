package com.githubstarredusers.domain.usecases

import com.githubstarredusers.domain.Repository
import com.githubstarredusers.domain.models.GitHubUserProfile
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetGitHubUserProfileUseCaseTest {

    private lateinit var repository: Repository
    private lateinit var useCase: GetGitHubUserProfileUseCase

    private val coroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        repository = mockk()

        useCase = GetGitHubUserProfileUseCase(repository)
    }

    @Test
    fun `when use case is executed then repository loadUserProfile should be called`() =
        coroutineDispatcher.runBlockingTest {
            val login = "login"
            val forceRefresh = false
            val gitHubUserProfile = buildGitHubUserProfile()
            val flow = flow {
                emit(gitHubUserProfile)
            }

            coEvery {
                repository.loadUserProfile(login, forceRefresh)
            } returns flow

            useCase.invoke(login)

            coVerify {
                repository.loadUserProfile(login, forceRefresh)
            }
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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

}
