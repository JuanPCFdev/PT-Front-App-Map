package com.jpdev.ptapplicationmap.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object LoginScreenRoute

@Serializable
data class MapScreenRoute(
    val lat: Double,
    val lng: Double,
)