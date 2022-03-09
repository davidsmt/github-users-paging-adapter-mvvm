package com.githubstarredusers.data.repository.datasources.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "git_hub_user_profiles")
@TypeConverters(RepositoryConverter::class)
data class GitHubUserProfileDb(
    @PrimaryKey @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "followers_total_count") val followersTotalCount: Int = 0,
    @ColumnInfo(name = "following_total_count") val followingTotalCount: Int = 0,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "starred_repositories") var starredRepositories: List<GitHubRepositoryDb>? = null,
    @ColumnInfo(name = "top_repositories") var topRepositories: List<GitHubRepositoryDb>? = null
)