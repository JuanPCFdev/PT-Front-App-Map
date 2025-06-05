package com.jpdev.ptapplicationmap.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jpdev.ptapplicationmap.R
import com.jpdev.ptapplicationmap.ui.theme.DarkBackground
import com.jpdev.ptapplicationmap.ui.theme.OnBackground
import com.jpdev.ptapplicationmap.ui.theme.OnPrimary
import com.jpdev.ptapplicationmap.ui.theme.Primary

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val user by viewModel.user.collectAsState("")
    val password by viewModel.password.collectAsState("")
    var passwordVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Title()
                Spacer(modifier = Modifier.height(24.dp))
                Form(
                    user = user,
                    onUserChange = { viewModel.onUserChange(it) },
                    password = password,
                    onPasswordChange = { viewModel.onPasswordChange(it) },
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityChange = { passwordVisible = !passwordVisible }
                )
                Spacer(modifier = Modifier.height(16.dp))
                LoginButton()
            }

            Text(
                "Todos los derechos reservados © 2025",
                color = OnBackground,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            )
        }
    }
}

@Composable
private fun Title() {
    Text(
        "APP NAME",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = OnBackground
    )
}

@Composable
private fun Form(
    user: String,
    onUserChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = user,
        onValueChange = onUserChange,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Primary,
            unfocusedBorderColor = OnBackground,
            cursorColor = Primary
        ),
        label = { Text("Usuario", color = OnBackground) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Contraseña", color = OnBackground) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Primary,
            unfocusedBorderColor = OnBackground,
            cursorColor = Primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onPasswordVisibilityChange) {
                Icon(
                    painter = if (passwordVisible)
                        painterResource(R.drawable.ic_visibility)
                    else
                        painterResource(R.drawable.ic_visibility_off),
                    contentDescription = if (passwordVisible)
                        "Ocultar contraseña"
                    else
                        "Mostrar contraseña"
                )
            }
        }
    )
}

@Composable
private fun LoginButton() {
    ElevatedButton(
        onClick = { /* TODO */ },
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = OnPrimary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text("Iniciar sesión", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
    }
}