package com.example.presentation.screens.auth


import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.presentation.components.AppOutlinedTextField
import com.example.presentation.components.AppOutlinedTextFieldPassword
import com.example.presentation.components.AuthCard
import com.example.presentation.components.LoadingProgress
import com.example.presentation.theme.ComposeChatAppTheme
import com.example.presentation.theme.Purple500
import com.example.presentation.ui.view_models.ApplicationViewModel
import com.example.presentation.ui.view_models.AuthViewModel
import com.example.presentation.utiles.Routes

@Composable
fun LoginScreen(
    mainNavController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    applicationViewModel: ApplicationViewModel = hiltViewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val state = authViewModel.authState.value

    ComposeChatAppTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(), verticalArrangement = Arrangement.Center
        ) {
            AuthCard(
                backgroundColor = Purple500,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(13.dp),
                elevation = 20.dp
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 10.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Login", fontSize = 16.sp)
                    }
                    AppOutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        hint = "Email",
                        hintColor = Color.White
                    )
                    AppOutlinedTextFieldPassword(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        hint = "Password",
                        hintColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff
                            val description =
                                if (passwordVisible) "Hide password" else "Show password"
                            IconButton(
                                onClick = { passwordVisible = !passwordVisible },

                                ) {
                                Icon(
                                    imageVector = image,
                                    contentDescription = description,
                                    tint = Color.White
                                )
                            }
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { mainNavController.navigate(Routes().forgetPasswordScreen) },
                            colors = ButtonDefaults.textButtonColors(
                                backgroundColor = Color.Transparent,
                                contentColor = Color.White,
                                disabledContentColor = Color.White
                            )
                        ) {
                            Text(text = "Forget Password?")
                        }

                    }

                    Text(
                        text = "Don't have an account? Sign Up",
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .clickable {

                                mainNavController.navigate(Routes().signUpRoute) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                    )

                    if (state.isLoading) {
                        LoadingProgress(dialogState = true, onDismissRequest = { })
                    } else if (state.error.isNotEmpty()) {
                        LoadingProgress(dialogState = false, onDismissRequest = { })
                        Toast.makeText(
                            applicationViewModel.application,
                            state.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (state.authState.isNotEmpty()) {
                        LoadingProgress(dialogState = false, onDismissRequest = { })
                        Toast.makeText(
                            applicationViewModel.application,
                            state.authState,
                            Toast.LENGTH_SHORT
                        ).show()
                        mainNavController.navigate(Routes().homeRoute) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(this.popUpToId) {
                                saveState = true
                            }
                            mainNavController.popBackStack()

                        }
                        authViewModel.rest()

                    }


                    OutlinedButton(
                        onClick = {
                            when {
                                email.trim().isEmpty() -> {
                                    Toast.makeText(
                                        applicationViewModel.application,
                                        "check your email",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> {
                                    Toast.makeText(
                                        applicationViewModel.application,
                                        "Please enter a valid email address",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                password.trim().length < 6 -> {
                                    Toast.makeText(
                                        applicationViewModel.application,
                                        "Password should consist of minimum 6 characters",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                else -> {
                                    authViewModel.signIn(email.trim(), password.trim())
                                }
                            }


                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color.White),
                        shape = RoundedCornerShape(13.dp),
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .width(150.dp)

                    ) {
                        Text(
                            text = "Log In",
                            color = Purple500,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                }
            }


        }
    }
}

