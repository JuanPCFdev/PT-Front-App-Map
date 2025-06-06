package com.jpdev.ptapplicationmap.data.remote

import com.jpdev.ptapplicationmap.data.dto.CoordinatesDto
import com.jpdev.ptapplicationmap.data.dto.LoginRequestDto
import com.jpdev.ptapplicationmap.data.dto.LoginResponseDto
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class AuthApiServiceUnitTest {

    // Mock del cliente Retrofit (interfaz AuthClient)
    private val mockClient = mockk<AuthClient>(relaxed = true)
    private lateinit var apiService: AuthApiService

    @Before
    fun setup() {
        apiService = AuthApiService(mockClient)
    }

    @Test
    fun `cuando AuthClient devuelve success true, se retorna el DTO tal cual`() = runTest {
        val request = LoginRequestDto("x@y.com", "123")
        val expected = LoginResponseDto(
            success = true,
            message = "OK",
            coordinates = CoordinatesDto(lat = 5.0, lng = 6.0)
        )

        // Simulamos que AuthClient.login() devuelve success=true
        coEvery { mockClient.login(request) } returns expected

        val result = apiService.getLoginResponse(request)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `cuando AuthClient devuelve success false, se retorna DTO con success false y mensaje de error`() =
        runTest {
            val request = LoginRequestDto("a@b.com", "pw")
            val respuestaServer = LoginResponseDto(
                success = false,
                message = "Credenciales inválidas",
                coordinates = null
            )
            coEvery { mockClient.login(request) } returns respuestaServer

            val result = apiService.getLoginResponse(request)

            assertThat(result.success).isFalse()
            assertThat(result.message).isEqualTo("Credenciales inválidas")
        }

    @Test
    fun `cuando AuthClient lanza HttpException 401, se captura y se retorna mensaje de credenciales inválidas`() =
        runTest {
            val request = LoginRequestDto("u@v.com", "pw")
            // Preparamos un HttpException con código 401
            val errorResponse = Response.error<LoginResponseDto>(
                401,
                ResponseBody.create(null, "")
            )
            val httpEx = HttpException(errorResponse)
            coEvery { mockClient.login(request) } throws httpEx

            val result = apiService.getLoginResponse(request)

            assertThat(result.success).isFalse()
            assertThat(result.message).isEqualTo("Credenciales inválidas")
        }

    @Test
    fun `cuando AuthClient lanza IOException, se retorna mensaje de error de conexión`() =
        runTest {
            val request = LoginRequestDto("foo@bar.com", "pw")
            coEvery { mockClient.login(request) } throws java.io.IOException("Sin red")

            val result = apiService.getLoginResponse(request)

            assertThat(result.success).isFalse()
            assertThat(result.message).contains("Error de conexión")
        }

}