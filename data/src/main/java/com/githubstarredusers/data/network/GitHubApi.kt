package com.githubstarredusers.data.network

import com.githubstarredusers.data.repository.datasources.remote.models.GitHubUserProfileResponse
import com.githubstarredusers.data.repository.datasources.remote.models.GitHubUsersResponse
import com.githubstarredusers.data.repository.datasources.remote.models.GithubRepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/users?q=followers%3A%3E%3D1000&ref=s=followers&o=desc")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): GitHubUsersResponse

    @GET("users/{login}")
    suspend fun getUserProfile(@Path("login") login: String): GitHubUserProfileResponse

    @GET("users/{login}/repos")
    suspend fun getRepositories(@Path("login") login: String): List<GithubRepositoryResponse>

    @GET("users/{login}/starred")
    suspend fun getStarred(@Path("login") login: String): List<GithubRepositoryResponse>

}