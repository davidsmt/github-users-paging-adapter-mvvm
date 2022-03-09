package com.githubstarredusers.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.githubstarredusers.domain.models.GitHubUserProfile
import com.githubstarredusers.domain.usecases.GetGitHubUserProfileUseCase
import com.githubstarredusers.presentation.coroutines.CoroutineContextProvider
import com.githubstarredusers.presentation.mappers.GitHubUserProfileUiMapper
import com.githubstarredusers.presentation.models.GitHubUserProfileUiModel
import com.githubstarredusers.presentation.models.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class GitHubUserProfileViewModel @Inject constructor(
    private val getGitHubUserProfileUseCase: GetGitHubUserProfileUseCase,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    private lateinit var _login: String

    private val state: MutableStateFlow<UiState<GitHubUserProfileUiModel>> =
        MutableStateFlow(UiState.Loading())
    val uiState = state.asLiveData()

    fun start(login: String) {
        _login = login
        viewModelScope.launch(coroutineContextProvider.io()) {
            getGitHubUserProfileUseCase(_login).onStart {
                state.emit(UiState.Loading())
            }.catch { e ->
                state.emit(UiState.Error(e))
            }.collect {
                updateUiState(it)
            }
        }
    }

    private suspend fun updateUiState(profile: GitHubUserProfile) {
        state.emit(UiState.Loaded(GitHubUserProfileUiMapper.map(profile)))
    }

    fun onRefresh() {
        viewModelScope.launch(coroutineContextProvider.io()) {
            getGitHubUserProfileUseCase(_login, true).onStart {
                state.emit(UiState.Loading())
            }.catch { e ->
                state.emit(UiState.Error(e))
            }.collect {
                updateUiState(it)
            }
        }
    }
}