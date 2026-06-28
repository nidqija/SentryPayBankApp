package com.example.sentrypaybank.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation


sealed class NavBar(val route : String){
    object Signin : NavBar("signin")
    object HomePage : NavBar("home")

}


