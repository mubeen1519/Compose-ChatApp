package com.example.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.presentation.screens.ChatScreen
import com.example.presentation.screens.home_screen.HomeScreen
import com.example.presentation.screens.auth.LoginScreen
import com.example.presentation.screens.MainScreen
import com.example.presentation.screens.auth.ForgetPasswordScreen
import com.example.presentation.screens.auth.SignUpScreen
import com.example.presentation.utiles.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    // permission granted
                } else {
                    // permission denied, but should I show a rationale?
                }
            }

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // permission granted
            } else {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            App(this, rememberNavController())
        }
    }
}

@Composable
fun App(mainActivity: MainActivity,mainNavController: NavHostController) {


        NavHost(
            navController = mainNavController,
            startDestination = Routes().mainRoute
        ) {

            composable(route = Routes().mainRoute) {
                MainScreen(mainActivity, mainNavController)

            }
            composable(route = Routes().loginRoute) {
                LoginScreen(mainNavController = mainNavController)
            }

            composable(route = Routes().signUpRoute) {
                SignUpScreen(mainNavController = mainNavController)
            }

            composable(route = Routes().homeRoute) {
                HomeScreen(mainNavController = mainNavController)
            }

            composable(route = Routes().chatScreen + "/{userId}") { navBackStack ->
                val userId = navBackStack.arguments?.getString("userId")

                ChatScreen(userId!!)
            }

            composable(route = Routes().forgetPasswordScreen){
                ForgetPasswordScreen(mainNavHostController = mainNavController)
            }


        }


}





