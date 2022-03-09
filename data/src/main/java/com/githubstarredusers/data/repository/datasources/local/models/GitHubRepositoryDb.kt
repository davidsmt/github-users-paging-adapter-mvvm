package com.githubstarredusers.data.repository.datasources.local.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class GitHubRepositoryDb(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "owner") val owner: GitHubRepositoryOwnerDb,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "stargazerCount") val stargazerCount: Int
)