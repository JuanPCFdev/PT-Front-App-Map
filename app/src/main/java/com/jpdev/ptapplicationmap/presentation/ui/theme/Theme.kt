package com.jpdev.ptapplicationmap.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

//Aunque vamos a usar un tema oscuro, podemos dejar listo el tema oscuro automatico en caso de que queramos usar colores adaptables
private val DarkColorScheme = darkColorScheme(
    primary           = Primary,
    onPrimary         = OnPrimary,
    secondary         = Secondary,
    onSecondary       = OnSecondary,
    background        = DarkBackground,
    onBackground      = OnBackground,
    surface           = DarkSurface,
    onSurface         = OnBackground,
    error             = ErrorColor,
    onError           = OnError,
)
//Solo en caso de que queramos usar colores adaptables al tema del dispositivo
private val LightColorScheme = lightColorScheme(
    primary           = Primary,
    onPrimary         = OnPrimary,
    secondary         = Secondary,
    onSecondary       = OnSecondary,
    background        = Color(0xFFF2F2F2),    //gris claro
    onBackground      = Color(0xFF000000),
    surface           = Color(0xFFFFFFFF),
    onSurface         = Color(0xFF000000),
    error             = ErrorColor,
    onError           = OnError,
)

@Composable
fun PTApplicationMapTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}