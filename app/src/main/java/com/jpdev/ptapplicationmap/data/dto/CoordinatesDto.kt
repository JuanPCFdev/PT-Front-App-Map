package com.jpdev.ptapplicationmap.data.dto

import com.google.gson.annotations.SerializedName

/// Clase que representa las coordenadas de un punto, es nuestro DTO
/// Como DTo tiene SerializedName que ayuda a mapear los campos del json
data class CoordinatesDto(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)