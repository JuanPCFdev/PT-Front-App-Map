package com.jpdev.ptapplicationmap.data.dto

import com.google.gson.annotations.SerializedName

//Esta clase nos ayuda a mapear la respuesta del servidor luego de usar LoginRequestDto
//con SerializedName podemos mapear el boddy del json. Se inicializa con valores nulos
//Y lo manejamos con null safety y evitamos el uso de !! y el NullPointerException.
data class LoginResponseDto(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("coordinates") val coordinates: CoordinatesDto? = null,
)

