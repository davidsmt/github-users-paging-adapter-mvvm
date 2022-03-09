package com.githubstarredusers.data.repository.datasources.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubRepositoryResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "owner") val owner: Owner,
    @Json(name = "language") val language: String?,
    @Json(name = "stargazers_count") val stargazersCount: Int?
)

@JsonClass(generateAdapter = true)
data class Owner(
    @Json(name = "avatar_url") val avatarUrl: String?,
    @Json(name = "id") val id: Int,
    @Json(name = "login") val login: String
)