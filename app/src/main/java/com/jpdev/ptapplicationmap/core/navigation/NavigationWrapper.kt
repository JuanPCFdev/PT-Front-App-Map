package com.jpdev.ptapplicationmap.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jpdev.ptapplicationmap.core.navigation.*
import com.jpdev.ptapplicationmap.presentation.screen.LoginScreen
import com.jpdev.ptapplicationmap.presentation.screen.MapScreen

/*NavigationWrapper
*
* Contiene toda la navegación de la app utilizando objetos y data classes
* que son serializables para indicar la ruta.
* */
@Composable
fun NavigationWrapper() {
    //Creamos un controlador de navegación
    val navController = rememberNavController()
    //Creamos un NavHost para controlar la navegación
    NavHost(navController = navController, startDestination = LoginScreenRoute) {
        //Definimos las rutas, en este caso la pantalla de login.
        composable<LoginScreenRoute> {
            LoginScreen(
                //Definimos el callback para la navegación hacia el mapa
                navigateToMap = { lat, lng ->
                    navController.navigate(
                        //Pasamos los parámetros de latitud y longitud al mapa
                        MapScreenRoute(
                            lat = lat,
                            lng = lng
                        )
                    )
                }
            )
        }
        //Definimos la ruta del mapa, navBackStackEntry es el objeto que contiene la información de la ruta
        composable<MapScreenRoute> { navBackStackEntry ->
            //Extraemos los parámetros de latitud y longitud de la ruta
            val backStackEntry = navBackStackEntry.toRoute<MapScreenRoute>()
            MapScreen(
                lat = backStackEntry.lat,
                lng = backStackEntry.lng,
                onClose = { //Definimos el callback para la navegación hacia atrás
                    navController.popBackStack()
                }
            )
        }
    }
}