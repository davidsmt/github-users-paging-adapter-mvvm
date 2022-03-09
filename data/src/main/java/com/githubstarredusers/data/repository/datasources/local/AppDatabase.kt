package com.githubstarredusers.data.repository.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.githubstarredusers.data.repository.datasources.local.daos.GitHubUserProfileDao
import com.githubstarredusers.data.repository.datasources.local.models.GitHubUserProfileDb

@Database(
    entities = [
        GitHubUserProfileDb::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gitHubUserProfileDao(): GitHubUserProfileDao

    companion object {

        const val DATABASE_NAME = "github-users-app-database"

    }
}