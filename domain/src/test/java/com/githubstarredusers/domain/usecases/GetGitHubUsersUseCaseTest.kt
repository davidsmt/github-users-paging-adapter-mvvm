package com.githubstarredusers.domain.usecases

import androidx.paging.PagingData
import com.githubstarredusers.domain.Repository
import com.githubstarredusers.domain.models.GitHubUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetGitHubUsersUseCaseTest {

    private lateinit var repository: Repository
    private lateinit var useCase: GetGitHubUsersUseCase

    private val coroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        repository = mockk()

        useCase = GetGitHubUsersUseCase(repository)
    }

    @Test
    fun `when use case is executed then repository loadUsers should be called`() =
        coroutineDispatcher.runBlockingTest {
            val flow = flowOf<PagingData<GitHubUser>>()

            coEvery {
                repository.loadUsers()
            } returns flow

            useCase.invoke()

            coVerify {
                repository.loadUsers()
            }
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
