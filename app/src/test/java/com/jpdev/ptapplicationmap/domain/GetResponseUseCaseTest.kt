package com.jpdev.ptapplicationmap.domain

import com.google.common.truth.Truth.assertThat
import com.jpdev.ptapplicationmap.data.dto.CoordinatesDto
import com.jpdev.ptapplicationmap.data.dto.LoginRequestDto
import com.jpdev.ptapplicationmap.data.dto.LoginResponseDto
import com.jpdev.ptapplicationmap.data.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetResponseUseCaseTest {
    //Mock del repositorio
    private val mockRepository = mockk<AuthRepository>(relaxed = true)
    private lateinit var useCase: GetResponseUseCase

    @Before
    fun setup() {
        useCase = GetResponseUseCase(mockRepository)
    }

    @Test
    fun `cuando el repositorio retorna success true, el caso de uso devuelve el mismo DTO`() =
        runTest {
            val request = LoginRequestDto(email = "juan@example.com", password = "123456")
            val expected = LoginResponseDto(
                success = true,
                message = "OK",
                coordinates = CoordinatesDto(lat = 10.0, lng = 20.0)
            )

            //Mockeamos el comportamiento del repositorio
            coEvery { mockRepository.getLoginResponse(request) } returns expected

            //Cuando ejecutamos el caso de uso
            val result = useCase(request)
            //Entonces obtenemos exactamente el mismo objeto
            assertThat(result).isEqualTo(expected)

        }

    @Test
    fun `cuando el repositorio lanza excepción, el caso de uso la propaga`() = runTest {
        val request = LoginRequestDto(email = "a@b.com", password = "pw")
        coEvery { mockRepository.getLoginResponse(request) } throws RuntimeException("fail")

        try {
            useCase(request)
            // Si no lanza excepción, el test falla
            assert(false) { "Se esperaba excepción" }
        } catch (e: RuntimeException) {
            assertThat(e).hasMessageThat().isEqualTo("fail")
        }
    }

}