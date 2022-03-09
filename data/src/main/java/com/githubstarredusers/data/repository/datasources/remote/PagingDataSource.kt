package com.githubstarredusers.data.repository.datasources.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.githubstarredusers.domain.models.GitHubUser
import retrofit2.HttpException
import java.io.IOException

class PagingDataSource(
    private val remoteDataSource: RemoteDataSource,
) : PagingSource<Int, GitHubUser>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubUser> {
        return try {
            val position = params.key ?: INIT_PAGE
            val data = remoteDataSource.loadUsers(params.key ?: 1, PAGE_SIZE)

            LoadResult.Page(
                data = data,
                prevKey = if (position == INIT_PAGE) null else position - 1,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GitHubUser>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    companion object {
        private const val INIT_PAGE = 1
        const val PAGE_SIZE = 10
    }

}
