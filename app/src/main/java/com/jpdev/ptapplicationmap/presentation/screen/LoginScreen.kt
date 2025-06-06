package com.jpdev.ptapplicationmap.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jpdev.ptapplicationmap.R
import com.jpdev.ptapplicationmap.presentation.ui.theme.DarkBackground
import com.jpdev.ptapplicationmap.presentation.ui.theme.DarkSurface
import com.jpdev.ptapplicationmap.presentation.ui.theme.ErrorColor
import com.jpdev.ptapplicationmap.presentation.ui.theme.OnBackground
import com.jpdev.ptapplicationmap.presentation.ui.theme.OnPrimary
import com.jpdev.ptapplicationmap.presentation.ui.theme.Primary
import com.jpdev.ptapplicationmap.presentation.ui.theme.Secondary
import com.jpdev.ptapplicationmap.presentation.viewmodel.LoginViewModel

//Login Screen es nuestro componente principal que contiene toda la pantalla de inicio de sesion.
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(), //Inyectamos el viewModel con hilt
    navigateToMap: (Double, Double) -> Unit, //Función que nos permite navegar a la pantalla del mapa.
) {
    //Recolectamos los datos del viewModel con sus flows
    val user by viewModel.user.collectAsState("")
    val password by viewModel.password.collectAsState("")
    var passwordVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    //Variables para la validación de los campos de texto
    val isEmailValid by viewModel.isEmailValid.collectAsState(false)
    val isPasswordValid by viewModel.isPasswordValid.collectAsState(false)
    val isFormValid by viewModel.isFormValid.collectAsState(false)

    //Variable de contexto para realizar los toast
    val context = LocalContext.current

    //Contenedor principal de la UI, usamos inner padding para el safeArea integrado con Scaffold
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            TestServer(onTestServer = {
                viewModel.testServer(context)
            })//FloatingActionButton para probar el servidor
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //Componente que contiene los campos del formulario de inicio de sesion
                Form(
                    user = user,
                    onUserChange = { viewModel.onUserChange(it) },
                    password = password,
                    onPasswordChange = { viewModel.onPasswordChange(it) },
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
                    isEmailValid = isEmailValid,
                    isPasswordValid = isPasswordValid,
                    isValidForm = isFormValid,
                    onFormSubmitted = {
                        viewModel.doLogin(navigateToMap = { latitude, longitude ->
                            navigateToMap(
                                latitude,
                                longitude
                            )
                        }, context = context)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                //Manejamos el estado de la UI con un when que muestra estados de carga, exito o error.
                when (uiState) {
                    is LoginViewModel.UiState.Error -> {
                        Text(
                            text = "Credenciales incorrectas o invalidas",
                            color = ErrorColor
                        )
                    }

                    is LoginViewModel.UiState.Loading -> {
                        CircularProgressIndicator(color = Primary)
                    }

                    is LoginViewModel.UiState.Success -> {
                        Text(
                            text = "VALIDACION EXITOSA",
                            color = Primary
                        )
                    }

                    else -> {

                    }

                }

            }
            //Componente que contiene información de uso para la demo.
            DemoInfo()
            //Componente extra para diseño
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

//Eeste componente nos permite realizar una llamada de prueba al servidor, para saber si este está en linea
@Composable
private fun TestServer(onTestServer: () -> Unit) {
    ElevatedButton(
        onClick = { onTestServer() },
        //containerColor = Primary,
        colors = ButtonDefaults.buttonColors(containerColor = Primary),
        modifier = Modifier
            .padding(bottom = 16.dp)
    ) {
        Text("Comprobar Servidor 💻", color = OnBackground, fontSize = 12.sp)
    }
}

//Informacion para el uso de la demo
@Composable
private fun DemoInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Para el demo, usa estas credenciales.", color = Color.LightGray, fontSize = 12.sp)
        Text("User: usuario@ejemplo.com", color = Color.LightGray, fontSize = 12.sp)
        Text("Password: 123456", color = Color.LightGray, fontSize = 12.sp)
    }
}

//Titulo de la pantalla
@Composable
private fun Title() {
    Text(
        "Find Me",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = OnBackground
    )
}

//Formulario principal del aplicativo
@Composable
private fun Form(
    user: String,
    onUserChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    isEmailValid: Boolean,
    isPasswordValid: Boolean,
    isValidForm: Boolean, onFormSubmitted: () -> Unit,
) {
    Card(
        modifier = Modifier.size(
            width = max(a = 300.dp, b = 400.dp),
            height = max(a = 360.dp, b = 400.dp)
        ),
        colors = CardDefaults.cardColors(
            containerColor = DarkSurface
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 32.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Title()

            Spacer(modifier = Modifier.height(32.dp))

            TextFieldUser(user, onUserChange, isEmailValid)

            Spacer(modifier = Modifier.height(12.dp))

            TextFieldPassword(
                password,
                onPasswordChange,
                isPasswordValid,
                passwordVisible,
                onPasswordVisibilityChange
            )

            Spacer(modifier = Modifier.height(20.dp))

            LoginButton(
                isValidForm = isValidForm,
                onFormSubmitted = onFormSubmitted
            )

        }
    }
}

@Composable
private fun TextFieldUser(user: String, onUserChange: (String) -> Unit, isEmailValid: Boolean) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = user,
        onValueChange = onUserChange,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isEmailValid) Primary else ErrorColor,
            unfocusedBorderColor = OnBackground,
            cursorColor = Primary,
            focusedLabelColor = OnBackground,
            unfocusedLabelColor = Color.Gray,
            focusedTextColor = OnBackground,
            unfocusedTextColor = OnBackground
        ),
        shape = RoundedCornerShape(12.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_user),
                contentDescription = "User icon",
                tint = Color.Gray
            )
        },
        label = { Text("Usuario") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        singleLine = true
    )
}

@Composable
private fun TextFieldPassword(
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordValid: Boolean,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Contraseña") },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isPasswordValid) Primary else ErrorColor,
            unfocusedBorderColor = OnBackground,
            cursorColor = Primary,
            focusedLabelColor = OnBackground,
            unfocusedLabelColor = Color.Gray,
            focusedTextColor = OnBackground,
            unfocusedTextColor = OnBackground
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_lock),
                contentDescription = "Password icon",
                tint = Color.Gray
            )
        },
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
                        "Mostrar contraseña",
                    tint = Color.Gray
                )
            }
        }
    )
}

//Boton de inicio de sesion, recibe un booleano que indica si el formulario es valido o no,
// y una funcion que se ejecuta al hacer click.
@Composable
private fun LoginButton(isValidForm: Boolean, onFormSubmitted: () -> Unit) {
    ElevatedButton(
        onClick = onFormSubmitted,
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = OnPrimary,
            disabledContainerColor = Secondary,
            disabledContentColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth(),
        enabled = isValidForm
    ) {
        Text("Iniciar sesión", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
    }
}