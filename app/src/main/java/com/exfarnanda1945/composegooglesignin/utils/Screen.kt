package com.exfarnanda1945.composegooglesignin.utils

sealed class Screen(val route:String){
    object SignInScreen: Screen("sign_in_screen")
    object ProfileScreen: Screen("profile_screen")
}
