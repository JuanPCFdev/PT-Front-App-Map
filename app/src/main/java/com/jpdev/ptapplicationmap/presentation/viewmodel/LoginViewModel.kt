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
    //Flow para manejar el estado del campo de usuario
    private var _user: MutableStateFlow<String> = MutableStateFlow("")
    val user: StateFlow<String> get() = _user
    //Flow para manejar el estado del campo de password
    private var _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> get() = _password
    //Flow para manejar el estado de la UI
    private var _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val uiState: StateFlow<UiState> get() = _uiState
    //Flows para manejar las validaciones del email
    private var _isEmailValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEmailValid: StateFlow<Boolean> get() = _isEmailValid
    //Flows para manejar las validaciones del password
    private var _isPasswordValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isPasswordValid: StateFlow<Boolean> get() = _isPasswordValid
    //Flows para manejar las validaciones del formulario, email y password
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
            //Estructuramos el usuario y el login en un LoginRequestDto
            val request = LoginRequestDto(email = _user.value, password = _password.value)
            //Realizamos la llamada con el caso de uso, enviando el LoginRequestDto
            val result: LoginResponseDto = getResponseUseCase(request) //Realizamos la llamada
            //Si la respuesta trae success == true, devolvemos el objeto tal cual.
            //En otro caso, devolvemos un LoginResponseDto con success = false y un mensaje de error.
            if (result.success == true) {
                //Si el login es exitoso, cambiamos estado de la UI y mostramos un toast
                _uiState.value = UiState.Success
                //Aqui hacemos una doble confirmación, para verificar que las coordenadas llegaron bien
                if (result.coordinates?.lat != null) {
                    //Si llegaron bien, mostramos un toast y navegamos al mapa enviando las coordenadas
                    Toast.makeText(context, "Inicio de sesion exitoso. Ingresando al mapa", Toast.LENGTH_LONG).show()
                    navigateToMap(result.coordinates.lat, result.coordinates.lng)

                    //Limpiar datos
                    _user.value = ""
                    _password.value = ""
                    _uiState.value = UiState.Empty
                }
            } else {
                //Si el login falla, devolvemos un error con el uiState
                _uiState.value =
                    UiState.Error(result.message ?: "Error credenciales incorrectas o invalidas")
                //Tambien desplegamos un toast para indicar el error
                Toast.makeText(context, "Error credenciales incorrectas o invalidas", Toast.LENGTH_LONG).show()
            }

        }
    }

    //Función para probar el servidor, ya que se enciende por peticion y con delay
    fun testServer(context: Context){
        //Abrimos un scope de corrutinas para llamar al caso de uso
        viewModelScope.launch {
            //Realizamos la misma llamada que en el login, pero sin navegación
            _uiState.value = UiState.Loading
            val request = LoginRequestDto(email = "usuario@ejemplo.com", password = "123456")
            val result: LoginResponseDto = getResponseUseCase(request)
            //En caso de que el servidor se encienda, mostramos un toast de exito
            if(result.coordinates?.lat != null){
                Toast.makeText(context, "✔ El servidor ya está en linea! 🚀", Toast.LENGTH_LONG).show()
            }else{
                //En caso de que el servidor no se encienda, mostramos un toast de error
                Toast.makeText(context, "⛔ El servidor se está encendiendo, intentelo en unos segundos! ⌛", Toast.LENGTH_LONG).show()
            }
            //Limpiamos el estado de la UI
            _uiState.value = UiState.Empty
        }

    }
    //Funcion para manejar el flow del valor del user
    fun onUserChange(user: String) {
        _user.value = user
        validateEmail(user)
        validateForm()
    }
    //Funcion para manejar el flow del valor de la contraseña
    fun onPasswordChange(password: String) {
        _password.value = password
        validatePassword(password)
        validateForm()
    }
    //Funcion para validar el email
    private fun validateEmail(email: String) {
        _isEmailValid.value = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    //Funcion para validar la contraseña
    private fun validatePassword(password: String) {
        //Aqui podemos añadir mas validaciones si es necesario, como caracteres especiales, mayusculas, etc
        _isPasswordValid.value = password.length >= 5
    }
    //Funcion para validar el formulario, email y contraseña deben coincidir
    private fun validateForm() {
        _isFormValid.value = _isEmailValid.value && _isPasswordValid.value
    }

    //Clase sellada para manejar el estado de la UI
    sealed class UiState {
        data object Loading : UiState()
        data object Success : UiState()
        data object Empty : UiState()
        data class Error(val message: String) : UiState()
    }

}