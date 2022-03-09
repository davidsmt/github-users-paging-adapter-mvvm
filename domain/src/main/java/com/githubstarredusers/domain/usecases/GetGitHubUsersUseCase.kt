package com.githubstarredusers.domain.usecases

import com.githubstarredusers.domain.Repository
import javax.inject.Inject

class GetGitHubUsersUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.loadUsers()
}