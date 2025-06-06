package com.jpdev.ptapplicationmap.data.dto

import com.google.gson.annotations.SerializedName

// Clase con la cual realizamos la petición de login al servidor, con los datos de email y password
// preparados para mapeo.
data class LoginRequestDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)
