package com.githubstarredusers.domain

import androidx.paging.PagingData
import com.githubstarredusers.domain.models.GitHubUser
import com.githubstarredusers.domain.models.GitHubUserProfile
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun loadUsers(): Flow<PagingData<GitHubUser>>

    suspend fun loadUserProfile(login: String, forceRefresh: Boolean): Flow<GitHubUserProfile>

}