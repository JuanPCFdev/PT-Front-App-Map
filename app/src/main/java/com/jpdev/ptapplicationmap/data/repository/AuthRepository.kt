package com.jpdev.ptapplicationmap.data.repository

import com.jpdev.ptapplicationmap.data.dto.LoginRequestDto
import com.jpdev.ptapplicationmap.data.dto.LoginResponseDto
import com.jpdev.ptapplicationmap.data.remote.AuthApiService
import javax.inject.Inject

/**
 * AuthRepository
 *
 * Esta clase sirve como intermediaria entre la capa de datos
 * (AuthApiService) y la capa de dominio (casos de uso).
 *
 * @Inject constructor(api: AuthApiService)
 *  - Hilt sabe inyectar un AuthApiService (que, a su vez,
 *    necesita un AuthClient). De esta forma, la jerarquía
 *    de creación de objetos queda resuelta automáticamente:
 *
 *    AuthClient (NetworkModule) → AuthApiService → AuthRepository
 */
class AuthRepository @Inject constructor(
    private val api: AuthApiService,
) {
    /**
     * getLoginResponse()
     *
     * Simplemente delega la petición al AuthApiService y devuelve el DTO.
     */
    suspend fun getLoginResponse(loginRequestDto: LoginRequestDto): LoginResponseDto =
        api.getLoginResponse(loginRequestDto = loginRequestDto)
}