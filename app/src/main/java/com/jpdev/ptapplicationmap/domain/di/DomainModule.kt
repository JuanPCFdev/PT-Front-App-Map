package com.jpdev.ptapplicationmap.domain.di

import com.jpdev.ptapplicationmap.data.repository.AuthRepository
import com.jpdev.ptapplicationmap.domain.GetResponseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetResponseUseCase(repository: AuthRepository): GetResponseUseCase {
        return GetResponseUseCase(repository)
    }

}