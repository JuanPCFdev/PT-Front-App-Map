package com.jpdev.ptapplicationmap.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @Module
 *  - Indica que esta clase proveerá dependencias para el grafo de Hilt.
 *
 * @InstallIn(SingletonComponent::class)
 *  - Define el "alcance" (scope) de estas dependencias. En este caso:
 *    SingletonComponent abarca todo el ciclo de vida de la aplicación
 *    (equivalente a @Singleton). Es decir, todo lo que se provea aquí
 *    vivirá mientras la app esté en memoria.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * @Provides
     *  - Indica a Hilt que este método "provee" (genera) una instancia
     *    de Retrofit cuando alguien lo solicite como dependencia.
     *
     * @Singleton
     *  - Asegura que solo exista una instancia de Retrofit en toda la aplicación.
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://pt-back-end.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}