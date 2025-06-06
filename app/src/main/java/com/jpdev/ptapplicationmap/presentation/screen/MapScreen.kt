package com.jpdev.ptapplicationmap.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.jpdev.ptapplicationmap.R
import com.jpdev.ptapplicationmap.presentation.ui.theme.OnPrimary
import com.jpdev.ptapplicationmap.presentation.ui.theme.Primary

@Composable
fun MapScreen(lat: Double, lng: Double, onClose: () -> Unit) {
    //Creamos un estado para el tipo de mapa
    var mapType by remember { mutableStateOf(MapType.NORMAL) }
    //Recibimos la latitud y longitud y la inicializamos en un marcador
    val marker = LatLng(lat, lng)
    //Creamos un estado de camara para centrar el mapa en la latitud y longitud
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(marker, 15f)
    }
    //Creamos un estado para el dialogo de cerrar
    var showExitDialog by remember { mutableStateOf(false) }
    //Contenedor principal
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            //Mapa de google, le pasamos el marcador y la camara y el tipo de mapa
            MapFromGoogle(marker, cameraPositionState, mapType)
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .background(color = Primary, shape = CircleShape)
            ) {
                //Columna para botones de interacción
                Column(
                    modifier = Modifier
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    //Icono para mapa normal
                    IconButton(onClick = { mapType = MapType.NORMAL }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_normal_mapp),
                            contentDescription = "Mapa normal",
                            tint = if (mapType == MapType.NORMAL) Color.Black else OnPrimary
                        )
                    }
                    //Icono para mapa satelite
                    IconButton(onClick = { mapType = MapType.SATELLITE }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_map_satelite),
                            contentDescription = "Mapa normal",
                            tint = if (mapType == MapType.SATELLITE) Color.Black else OnPrimary
                        )
                    }
                    // Botón para MapType.TERRAIN
                    IconButton(onClick = { mapType = MapType.TERRAIN }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_map_terrain),
                            contentDescription = "Terreno",
                            tint = if (mapType == MapType.TERRAIN) Color.Black else OnPrimary
                        )
                    }
                    //Icono para mapa hibrido
                    IconButton(onClick = { mapType = MapType.HYBRID }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_map_hybrid),
                            contentDescription = "Mapa normal",
                            tint = if (mapType == MapType.HYBRID) Color.Black else OnPrimary
                        )
                    }
                    //Icono para salir
                    IconButton(onClick = { showExitDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Mapa normal",
                            tint = if (mapType == MapType.HYBRID) Color.Black else OnPrimary
                        )
                    }
                }
            }
            //Dialogo de salida, se debe confirmar para salir.
            ConfirmExitDialog(
                visible = showExitDialog,
                onConfirmExit = onClose,
                onDismiss = { showExitDialog = false }
            )
        }
    }
}

@Composable
private fun MapFromGoogle(marker: LatLng, camera: CameraPositionState, mapType: MapType) {

    //Reenderizamos el mapa
    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = camera,
        properties = MapProperties(
            mapType = mapType
        )
    ) {
        //Asignamos el marcador con las coordenadas
        Marker(
            state = rememberMarkerState(position = marker),
            title = "Bogotá",
            snippet = "Marker in bogota"
        )
    }

}

@Composable
private fun ConfirmExitDialog(
    visible: Boolean,
    onConfirmExit: () -> Unit,
    onDismiss: () -> Unit,
) {
    //Si el dialogo no es visible, no lo mostramos
    if (!visible) return
    //Si es visible, mostramos el dialogo y enviamos el callback desde el NavigationWrapper
    AlertDialog(
        onDismissRequest = {
            // Cuando el usuario toca fuera del diálogo o presiona “atrás”
            onDismiss()
        },
        title = { Text(text = "¿Deseas salir?") },
        text = { Text(text = "¿Estás seguro de que quieres salir de la aplicación?") },
        confirmButton = {
            TextButton(onClick = {
                onConfirmExit()
            }) {
                Text(text = "Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancelar")
            }
        }
    )
}