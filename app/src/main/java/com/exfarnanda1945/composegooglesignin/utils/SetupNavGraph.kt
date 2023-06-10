package com.exfarnanda1945.composegooglesignin.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.exfarnanda1945.composegooglesignin.presentation.profile.ProfileScreen
import com.exfarnanda1945.composegooglesignin.presentation.sign_in.SignInScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    context: Context
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.SignInScreen.route
    ) {
        composable(Screen.SignInScreen.route) {
            SignInScreen(navHostController)
        }

        composable(Screen.ProfileScreen.route) {
            ProfileScreen(
                navHostController = navHostController,
                context = context
            )
        }
    }

}