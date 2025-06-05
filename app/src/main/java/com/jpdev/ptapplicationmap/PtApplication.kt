package com.jpdev.ptapplicationmap

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @HiltAndroidApp
 * Anotamos la clase Application con @HiltAndroidApp para que Hilt genere
 * el grafo de dependencias en tiempo de compilación. Esto prepara:
 *  1. Una subclase de Application que maneja la inyección de dependencias.
 *  2. Un ContentProvider interno de Hilt que inicializa Dagger antes de
 *     que cualquiera de tus Activitys, Fragments o ViewModels sea creada.
 */
@HiltAndroidApp
class PtApplication : Application()