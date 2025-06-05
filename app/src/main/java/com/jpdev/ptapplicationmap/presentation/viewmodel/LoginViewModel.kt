package com.jpdev.ptapplicationmap.presentation.viewmodel

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpdev.ptapplicationmap.data.dto.LoginRequestDto
import com.jpdev.ptapplicationmap.data.dto.LoginResponseDto
import com.jpdev.ptapplicationmap.domain.GetResponseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @HiltViewModel
 *  - Marca esta clase como un ViewModel cuyos dependencies serán
 *    manejados por Hilt. Internamente, Hilt se encarga de crear
 *    un Factory que injecta los parámetros del constructor.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    /**
     * Hilt inyectará aquí un GetResponseUseCase. Gracias a que
     * en GetResponseUseCase está anotado @Inject constructor(...),
     * Hilt sabe cómo construirlo solo.
     */
    private val getResponseUseCase: GetResponseUseCase,
) : ViewModel() {

    private var _user: MutableStateFlow<String> = MutableStateFlow("")
    val user: StateFlow<String> get() = _user

    private var _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private var _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val uiState: StateFlow<UiState> get() = _uiState

    private var _isEmailValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEmailValid: StateFlow<Boolean> get() = _isEmailValid

    private var _isPasswordValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isPasswordValid: StateFlow<Boolean> get() = _isPasswordValid

    private var _isFormValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFormValid: StateFlow<Boolean> get() = _isFormValid


    /**
     *  Método que utilizamos para llamar al caso de uso.
     */
    fun doLogin(navigateToMap: (Double,Double) -> Unit, context: Context) {
        viewModelScope.launch {

            // Llamamos al use case; Hilt ya resolvió toda la cadena:
            // AuthClient → AuthApiService → AuthRepository → GetResponseUseCase.
            // Manejamos el estado de la UI con Flow.
            _uiState.value = UiState.Loading

            val request = LoginRequestDto(email = _user.value, password = _password.value)
            val result: LoginResponseDto = getResponseUseCase(request) //Realizamos la llamada
            if (result.success == true) {
                _uiState.value = UiState.Success
                if (result.coordinates?.lat != null) {
                    Toast.makeText(context, "Inicio de sesion exitoso. Ingresando al mapa", Toast.LENGTH_LONG).show()
                    navigateToMap(result.coordinates.lat, result.coordinates.lng)

                    //Limpiar datos
                    _user.value = ""
                    _password.value = ""
                    _uiState.value = UiState.Empty
                }
            } else {
                _uiState.value =
                    UiState.Error(result.message ?: "Error credenciales incorrectas o invalidas")
                Toast.makeText(context, "Error credenciales incorrectas o invalidas", Toast.LENGTH_LONG).show()
            }

        }
    }

    fun onUserChange(user: String) {
        _user.value = user
        validateEmail(user)
        validateForm()
    }

    fun onPasswordChange(password: String) {
        _password.value = password
        validatePassword(password)
        validateForm()
    }

    private fun validateEmail(email: String) {
        _isEmailValid.value = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validatePassword(password: String) {
        //Aqui podemos añadir mas validaciones si es necesario, como caracteres especiales, mayusculas, etc
        _isPasswordValid.value = password.length >= 5
    }

    private fun validateForm() {
        _isFormValid.value = _isEmailValid.value && _isPasswordValid.value
    }

    fun onSubmit(navigateToMap: () -> Unit) {

    }

    sealed class UiState {
        data object Loading : UiState()
        data object Success : UiState()
        data object Empty : UiState()
        data class Error(val message: String) : UiState()
    }

}