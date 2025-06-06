package com.jpdev.ptapplicationmap.domain.di

import com.jpdev.ptapplicationmap.data.repository.AuthRepository
import com.jpdev.ptapplicationmap.domain.GetResponseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
* El Domain Module nos permite proveer las dependencias de la capa de dominio.
* En este caso proveemos el caso de uso que vamos a utilizar para la petición.
* Aqui se suelen proveer todas las dependencias de casos de uso.
* */
@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    //Proveemos como singleton el caso de uso
    @Provides
    @Singleton
    fun provideGetResponseUseCase(repository: AuthRepository): GetResponseUseCase {
        return GetResponseUseCase(repository)
    }

}