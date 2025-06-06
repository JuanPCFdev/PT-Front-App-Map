package com.jpdev.ptapplicationmap.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object LoginScreenRoute //Este objeto representa la ruta de la pantalla de inicio de sesión.

@Serializable
data class MapScreenRoute( //Esta data class representa la ruta de la pantalla del mapa. cons sus parametros
    val lat: Double,
    val lng: Double,
)