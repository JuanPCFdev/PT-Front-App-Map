package com.jpdev.ptapplicationmap.data.repository

import com.jpdev.ptapplicationmap.data.dto.LoginRequestDto
import com.jpdev.ptapplicationmap.data.dto.LoginResponseDto
import com.jpdev.ptapplicationmap.data.remote.AuthApiService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AuthRepositoryTest {

    private val mockService = mockk<AuthApiService>(relaxed = true)
    private lateinit var repository: AuthRepository

    @Before
    fun setup() {
        repository = AuthRepository(mockService)
    }

    @Test
    fun `cuando AuthApiService devuelve success true, el repositorio retorna el mismo DTO`() =
        runTest {
            val request = LoginRequestDto("user@x.com", "pwd")
            val expected = LoginResponseDto(
                success = true,
                message = "Bienvenido",
                coordinates = null
            )

            coEvery { mockService.getLoginResponse(request) } returns expected

            val result = repository.getLoginResponse(request)

            assertThat(result).isEqualTo(expected)
        }

    @Test
    fun `cuando AuthApiService lanza IOException, el repositorio propaga la excepción`() = runTest {
        val request = LoginRequestDto("user", "pw")
        coEvery { mockService.getLoginResponse(request) } throws java.io.IOException("No red")

        try {
            repository.getLoginResponse(request)
            assert(false) { "Se esperaba IOException" }
        } catch (e: java.io.IOException) {
            assertThat(e).hasMessageThat().isEqualTo("No red")
        }
    }
}