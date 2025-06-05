package com.jpdev.ptapplicationmap.data.remote

import com.jpdev.ptapplicationmap.data.dto.LoginRequestDto
import com.jpdev.ptapplicationmap.data.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz que define los endpoints de autenticación.
 * Retrofit generará automáticamente una implementación
 * cuando llamemos a `retrofit.create(AuthClient::class.java)`.
 */
interface AuthClient {
    @POST("api/login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): LoginResponseDto
}