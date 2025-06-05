package com.jpdev.ptapplicationmap.data.remote

import com.jpdev.ptapplicationmap.data.dto.LoginRequestDto
import com.jpdev.ptapplicationmap.data.dto.LoginResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * AuthApiService
 *
 * Esta clase encapsula la llamada a la interfaz AuthClient y maneja
 * la lógica de hilo (Dispatchers.IO).
 *
 * @Inject constructor(client: AuthClient)
 *  - Con esta anotación, estamos diciendo a Hilt que para crear
 *    un AuthApiService, necesita una instancia de AuthClient.
 */
class AuthApiService @Inject constructor(
    private val client: AuthClient
) {

    /**
     * getLoginResponse()
     *
     * Llama al endpoint de login a través de AuthClient.
     * Se ejecuta en un dispatcher IO (hilo de trabajo) para no bloquear
     * el hilo principal (UI).
     *
     * Si la respuesta trae `success == true`, devolvemos el objeto
     * tal cual. En otro caso, devolvemos un LoginResponseDto con
     * success = false y un mensaje de error.
     */
    suspend fun getLoginResponse(loginRequestDto: LoginRequestDto): LoginResponseDto {
        return try {
            withContext(Dispatchers.IO) {
                val response = client.login(loginRequestDto)
                if (response.success == true) response
                else LoginResponseDto(false, response.message ?: "Error desconocido", null)
            }
        } catch (e: Exception) {
            handleNetworkError(e) // Manejo de errores
        }
    }

    private fun handleNetworkError(e: Exception): LoginResponseDto {
        return when (e) {
            is HttpException -> {
                // Error HTTP (4xx/5xx)
                val errorMessage = when (e.code()) {
                    401 -> "Credenciales inválidas"
                    500 -> "Error del servidor"
                    else -> "Error HTTP: ${e.code()}"
                }
                LoginResponseDto(false, errorMessage, null)
            }
            is IOException -> {
                // Problemas de red
                LoginResponseDto(false, "Error de conexión: ${e.message}", null)
            }
            else -> {
                // Otros errores inesperados
                LoginResponseDto(false, "Error inesperado: ${e.message}", null)
            }
        }
    }
}