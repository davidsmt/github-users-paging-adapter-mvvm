package com.githubstarredusers.data.repository.datasources.local

import com.githubstarredusers.data.repository.datasources.local.mappers.GitHubUserProfileDbMapper
import com.githubstarredusers.domain.models.GitHubUserProfile

class LocalDataSource(appDatabase: AppDatabase) {

    private val gitHubUserProfileDao = appDatabase.gitHubUserProfileDao()

    fun loadUser(login: String): GitHubUserProfile? {
        return gitHubUserProfileDao.getUser(login)?.let {
            GitHubUserProfileDbMapper.mapToDomain(it)
        }
    }

    fun updateUserProfile(gitHubUserProfile: GitHubUserProfile) {
        gitHubUserProfileDao.insert(GitHubUserProfileDbMapper.mapToDb(gitHubUserProfile))
    }

}