package com.githubstarredusers.data.repository.datasources.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubUserResponse(
    @Json(name = "id") val id: Long? = null,
    @Json(name = "login") val login: String? = null,
    @Json(name = "url") val url: String? = null,
    @Json(name = "avatar_url") val avatarUrl: String? = null
)