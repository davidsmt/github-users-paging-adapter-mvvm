package com.githubstarredusers.data.repository.datasources.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubUsersResponse(
    @Json(name = "total_count") val totalCount: Long? = null,
    @Json(name = "items") val items: List<GitHubUserResponse>? = null
)