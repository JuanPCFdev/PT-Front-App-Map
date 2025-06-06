package com.jpdev.ptapplicationmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jpdev.ptapplicationmap.core.navigation.NavigationWrapper
import com.jpdev.ptapplicationmap.presentation.ui.theme.PTApplicationMapTheme
import dagger.hilt.android.AndroidEntryPoint

//Definimos el entryPoint para Hilt
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Habilitamos el edgeToEdge para ocupar toda la pantalla
        // (como buena practica siempre hay que usar el safeArea con el enableEdgeToEdge)
        enableEdgeToEdge()
        //Definimos el tema y el wrapper de navegacion
        setContent {
            PTApplicationMapTheme {
                NavigationWrapper()
            }
        }
    }
}