package com.example.presentation.screens.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.presentation.components.AppOutlinedTextField
import com.example.presentation.components.AuthCard
import com.example.presentation.theme.ComposeChatAppTheme
import com.example.presentation.theme.Purple500
import com.example.presentation.ui.view_models.ApplicationViewModel
import com.example.presentation.ui.view_models.AuthViewModel

@Composable
fun ForgetPasswordScreen(
    mainNavHostController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    applicationViewModel: ApplicationViewModel = hiltViewModel()

) {
    var email by rememberSaveable { mutableStateOf("") }

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
                            .padding(10.dp)
                    ) {
                        Text(text = "Please Enter Your account email to reset your password")
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

                                else -> {
                                    authViewModel.resetPassword(email)
                                    Toast.makeText(
                                        applicationViewModel.application,
                                        "Please check your email to reset your password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    mainNavHostController.popBackStack()
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

                    ) {
                        Text(
                            text = "Reset Password",
                            color = Purple500,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                }
            }


        }
    }
}