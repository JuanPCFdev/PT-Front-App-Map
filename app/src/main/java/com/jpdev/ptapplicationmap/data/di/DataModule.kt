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
/*
* Data Module nos permite proveer dependencias de la capa de datos.
* En este caso proveemos el cliente de retrofit y el repositorio.
* */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    //Proveemos como singleton el cliente de retrofit
    @Provides
    @Singleton
    fun provideAuthClient(retrofit: Retrofit): AuthClient{
        return retrofit.create(AuthClient::class.java)
    }
    //Proveemos como singleton el repositorio
    @Provides
    @Singleton
    fun provideAuthRepository(apiService: AuthApiService): AuthRepository{
        return AuthRepository(apiService)
    }
}