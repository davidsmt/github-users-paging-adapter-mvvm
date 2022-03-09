package com.githubstarredusers.data.di

import android.content.Context
import androidx.room.Room
import com.githubstarredusers.data.network.GitHubApi
import com.githubstarredusers.data.repository.RepositoryImpl
import com.githubstarredusers.data.repository.datasources.local.AppDatabase
import com.githubstarredusers.data.repository.datasources.local.LocalDataSource
import com.githubstarredusers.data.repository.datasources.remote.RemoteDataSource
import com.githubstarredusers.domain.Repository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object RepositoryModule {

    @Provides
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): Repository {
        return RepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideRemoteDataSource(gitHubApi: GitHubApi): RemoteDataSource {
        return RemoteDataSource(gitHubApi)
    }

    @Provides
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi {
        return retrofit.create(GitHubApi::class.java)
    }

    @Provides
    fun provideLocalDataSource(appDatabase: AppDatabase): LocalDataSource {
        return LocalDataSource(appDatabase)
    }

    @Provides
    fun provideAppDatabase(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
}