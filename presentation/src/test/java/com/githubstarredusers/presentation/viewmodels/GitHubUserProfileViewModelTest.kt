package com.githubstarredusers.presentation.viewmodels

import androidx.arch.core.executor.ArchTaskExecutor
import com.githubstarredusers.domain.models.GitHubUserProfile
import com.githubstarredusers.domain.usecases.GetGitHubUserProfileUseCase
import com.githubstarredusers.presentation.models.GitHubUserProfileUiModel
import com.githubstarredusers.presentation.models.UiState
import com.githubstarredusers.presentation.utils.TestCoroutineContextProvider
import com.githubstarredusers.presentation.utils.TestTaskExecutor
import com.githubstarredusers.presentation.utils.TestableObserver
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GitHubUserProfileViewModelTest {

    private lateinit var getGitHubUserProfileUseCase: GetGitHubUserProfileUseCase
    private lateinit var viewModel: GitHubUserProfileViewModel

    private val coroutineDispatcher = TestCoroutineDispatcher()
    private val login = "login"

    @Before
    fun setUp() {
        ArchTaskExecutor.getInstance().setDelegate(TestTaskExecutor())
        Dispatchers.setMain(Dispatchers.Unconfined)

        getGitHubUserProfileUseCase = mockk()

        viewModel = GitHubUserProfileViewModel(
            getGitHubUserProfileUseCase,
            TestCoroutineContextProvider()
        )
    }

    @Test
    fun `when start and data has not been loaded yet then loading should be shown`() =
        coroutineDispatcher.runBlockingTest {
            //Arrange
            val stateObserver = TestableObserver<UiState<GitHubUserProfileUiModel>>()

            val gitHubUserProfile = buildGitHubUserProfile()
            val flow = flow {
                emit(gitHubUserProfile)
            }

            // When
            coEvery {
                getGitHubUserProfileUseCase(login)
            } returns flow

            viewModel.uiState.observeForever(stateObserver)
            viewModel.start(login)

            //Assert
            assertThat(stateObserver.history[0], instanceOf(UiState.Loading::class.java))
        }

    @Test
    fun `when start and data has been loaded then content should be shown`() =
        coroutineDispatcher.runBlockingTest {
            //Arrange
            val stateObserver = TestableObserver<UiState<GitHubUserProfileUiModel>>()

            val gitHubUserProfile = buildGitHubUserProfile()
            val flow = flow {
                emit(gitHubUserProfile)
            }

            // When
            coEvery {
                getGitHubUserProfileUseCase(login)
            } returns flow

            viewModel.uiState.observeForever(stateObserver)
            viewModel.start(login)

            //Assert
            assertThat(stateObserver.history[0], instanceOf(UiState.Loading::class.java))
            assertThat(stateObserver.history[1], instanceOf(UiState.Loaded::class.java))
        }

    @Test
    fun `when start and data has not been loaded yet then error should be shown`() =
        coroutineDispatcher.runBlockingTest {
            //Arrange
            val stateObserver = TestableObserver<UiState<GitHubUserProfileUiModel>>()

            val flow = flow<GitHubUserProfile> {
                throw Exception()
            }

            // When
            coEvery {
                getGitHubUserProfileUseCase(login)
            } returns flow

            viewModel.uiState.observeForever(stateObserver)
            viewModel.start(login)

            //Assert
            assertThat(stateObserver.history[0], instanceOf(UiState.Loading::class.java))
            assertThat(stateObserver.history[1], instanceOf(UiState.Error::class.java))
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
