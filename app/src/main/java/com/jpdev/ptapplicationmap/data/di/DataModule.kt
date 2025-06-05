package com.jpdev.ptapplicationmap.data.di

import com.jpdev.ptapplicationmap.data.remote.AuthApiService
import com.jpdev.ptapplicationmap.data.remote.AuthClient
import com.jpdev.ptapplicationmap.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAuthClient(retrofit: Retrofit): AuthClient{
        return retrofit.create(AuthClient::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: AuthApiService): AuthRepository{
        return AuthRepository(apiService)
    }
}