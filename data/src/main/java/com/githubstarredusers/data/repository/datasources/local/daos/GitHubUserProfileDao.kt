package com.githubstarredusers.data.repository.datasources.local.daos

import androidx.room.*
import com.githubstarredusers.data.repository.datasources.local.models.GitHubUserProfileDb

@Dao
interface GitHubUserProfileDao {

    @Query("SELECT * FROM git_hub_user_profiles WHERE login == :userLogin")
    fun getUser(userLogin: String): GitHubUserProfileDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gitHubUserProfile: GitHubUserProfileDb): Long

    @Delete
    fun delete(gitHubUserProfile: GitHubUserProfileDb)

    @Query("DELETE FROM git_hub_user_profiles")
    fun deleteAll()

}