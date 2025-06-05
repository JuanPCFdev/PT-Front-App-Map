package com.jpdev.ptapplicationmap.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jpdev.ptapplicationmap.core.navigation.*
import com.jpdev.ptapplicationmap.presentation.screen.LoginScreen
import com.jpdev.ptapplicationmap.presentation.screen.MapScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LoginScreenRoute) {

        composable<LoginScreenRoute> {
            LoginScreen(
                navigateToMap = { lat, lng ->
                    navController.navigate(
                        MapScreenRoute(
                            lat = lat,
                            lng = lng
                        )
                    )
                }
            )
        }

        composable<MapScreenRoute> { navBackStackEntry ->
            val backStackEntry = navBackStackEntry.toRoute<MapScreenRoute>()
            MapScreen(
                lat = backStackEntry.lat,
                lng = backStackEntry.lng,
                onClose = { navController.popBackStack() }
            )
        }


    }
}