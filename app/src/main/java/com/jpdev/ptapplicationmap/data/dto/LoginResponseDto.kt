package com.jpdev.ptapplicationmap.data.dto

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("coordinates") val coordinates: CoordinatesDto? = null,
)

