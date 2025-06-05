package com.jpdev.ptapplicationmap.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private var _user:MutableStateFlow<String> = MutableStateFlow("")
    val user: StateFlow<String> get() = _user

    private var _password:MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private var _uiState:MutableStateFlow<UiState> = MutableStateFlow(UiState.Success)
    val uiState: StateFlow<UiState> get() = _uiState

    init {
        
    }

    fun onUserChange(user: String) {
        _user.value = user
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSubmit(navigateToMap:()->Unit) {

    }

    sealed class UiState {
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}