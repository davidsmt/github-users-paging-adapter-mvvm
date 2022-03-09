package com.githubstarredusers.domain.usecases

import com.githubstarredusers.domain.Repository
import javax.inject.Inject

class GetGitHubUserProfileUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(login: String, forceRefresh: Boolean = false) = repository.loadUserProfile(login, forceRefresh)
}