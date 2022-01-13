package com.tuwaiq.rr.di

import com.tuwaiq.rr.data.repos.AuthRepoImpl
import com.tuwaiq.rr.data.repos.PostRepoImpl
import com.tuwaiq.rr.data.repos.ProfileRepoImpl
import com.tuwaiq.rr.domain.repos.AuthRepo
import com.tuwaiq.rr.domain.repos.PostRepo
import com.tuwaiq.rr.domain.repos.ProfileRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthRepo(): AuthRepo = AuthRepoImpl()

    @Provides
    @Singleton
    fun providePostRepo(): PostRepo = PostRepoImpl()

    @Provides
    @Singleton
    fun provideProfileRepo(): ProfileRepo = ProfileRepoImpl()

}