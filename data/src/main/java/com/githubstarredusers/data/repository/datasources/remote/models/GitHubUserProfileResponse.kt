package com.githubstarredusers.data.repository.datasources.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubUserProfileResponse(
    @Json(name = "avatar_url") val avatarUrl: String?,
    @Json(name = "company") val company: String?,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "followers") val followers: Int,
    @Json(name = "following") val following: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "login") val login: String,
    @Json(name = "name") val name: String,
    @Json(name = "public_repos") val publicRepos: Int,
    @Json(name = "twitter_username") val twitterUsername: String?,
    @Json(name = "email") val email: String?,
    @Json(name = "url") val url: String
)