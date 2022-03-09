package com.githubstarredusers.data.repository.datasources.local.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class RepositoryConverter {

    @TypeConverter
    fun toGitHubRepositoryDbList(jsonAsString: String?): List<GitHubRepositoryDb> {
        val gson = Gson()
        if (jsonAsString == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<GitHubRepositoryDb>>() {}.type
        return gson.fromJson(jsonAsString, listType)
    }

    @TypeConverter
    fun toGitHubRepositoryDbJsonAsString(list: List<GitHubRepositoryDb>?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(list)
    }

}
