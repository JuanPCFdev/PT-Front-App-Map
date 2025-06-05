package com.jpdev.ptapplicationmap.data.dto

import com.google.gson.annotations.SerializedName

data class CoordinatesDto(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)