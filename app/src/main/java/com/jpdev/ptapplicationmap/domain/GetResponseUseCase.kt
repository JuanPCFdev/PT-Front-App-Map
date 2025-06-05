package com.jpdev.ptapplicationmap.domain

import com.jpdev.ptapplicationmap.data.dto.LoginRequestDto
import com.jpdev.ptapplicationmap.data.dto.LoginResponseDto
import com.jpdev.ptapplicationmap.data.repository.AuthRepository
import javax.inject.Inject

/**
 * GetResponseUseCase
 *
 * Un caso de uso (use case) encapsula la lógica de negocio que se usará
 * en la capa de presentación. En este caso, “obtener la respuesta de login”.
 *
 * @Inject constructor(repository: AuthRepository)
 *  - Hilt sabe que para crear GetResponseUseCase necesita
 *    un AuthRepository (que, a su vez, necesita AuthApiService,
 *    y éste, AuthClient).
 *
 * Operador invoke:
 *  - Sobrecargamos `operator fun invoke()` para que al llamar
 *    directamente a la instancia de GetResponseUseCase (por ejemplo:
 *    `getResponseUseCase()`), se ejecute el bloque de código.
 */
class GetResponseUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(loginRequestDto: LoginRequestDto): LoginResponseDto =
        repository.getLoginResponse(loginRequestDto = loginRequestDto)

}