package com.githubstarredusers.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.githubstarredusers.domain.usecases.GetGitHubUsersUseCase
import com.githubstarredusers.presentation.coroutines.CoroutineContextProvider
import com.githubstarredusers.presentation.mappers.GitHubUsersUiMapper
import com.githubstarredusers.presentation.models.GitHubUserUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


class GitHubUsersViewModel @Inject constructor(
    private val getGitHubUsersUseCase: GetGitHubUsersUseCase,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    private val state: MutableStateFlow<PagingData<GitHubUserUiModel>> =
        MutableStateFlow(PagingData.empty())
    val uiState: StateFlow<PagingData<GitHubUserUiModel>> = state

    fun start() {
        viewModelScope.launch(coroutineContextProvider.io()) {
            getGitHubUsersUseCase()
                .cachedIn(viewModelScope).map { pagingData ->
                    pagingData.map {
                        GitHubUsersUiMapper.map(it)
                    }
                }.collectLatest {
                    state.value = it
                }
        }
    }

}