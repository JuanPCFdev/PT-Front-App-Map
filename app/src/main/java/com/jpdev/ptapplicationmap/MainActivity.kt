package com.jpdev.ptapplicationmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jpdev.ptapplicationmap.core.navigation.NavigationWrapper
import com.jpdev.ptapplicationmap.presentation.ui.theme.PTApplicationMapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PTApplicationMapTheme {
                NavigationWrapper()
            }
        }
    }
}