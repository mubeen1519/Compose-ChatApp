package com.example.presentation.screens.home_screen.bottom_navigation_screen


import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.presentation.components.DefaultAppBar
import com.example.presentation.screens.home_screen.tabs_screen.ChatsScreen
import com.example.presentation.screens.home_screen.tabs_screen.UsersScreen
import com.example.presentation.theme.ComposeChatAppTheme
import com.example.presentation.ui.view_models.AuthViewModel
import com.example.presentation.ui.view_models.HomePageViewModel
import com.example.presentation.utiles.Routes


@Composable
fun HomePage(
    mainNavController: NavHostController,
    homePageViewModel: HomePageViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val tabsNavController = rememberNavController()
    val navBackStackEntry by tabsNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ComposeChatAppTheme {

        Scaffold(
            topBar = {
                DefaultAppBar(
                    tabsNavController = tabsNavController,
                    actions = {
                        IconButton(onClick = {
                            authViewModel.userLogOut()
                            mainNavController.navigate(Routes().loginRoute) {
                                popUpTo(Routes().loginRoute) {
                                    mainNavController.popBackStack()
                                }

                            }

                        }) {
                            Icon(
                                imageVector = Icons.Filled.ExitToApp,
                                contentDescription = "log out",
                                tint = Color.White

                            )
                        }
                    },
                    title = { Text(text = "Home") },

                    )

            },


            ) {
            NavHost(
                navController = tabsNavController,
                startDestination = Routes().chatsTabRoute
            ) {
                composable(route = Routes().chatsTabRoute) {
                    ChatsScreen(mainNavController)

                }
                composable(route = Routes().usersTabRoute) {
                    UsersScreen(mainNavController, homePageViewModel.usersState.value.users)

                }
            }
        }
    }
}





