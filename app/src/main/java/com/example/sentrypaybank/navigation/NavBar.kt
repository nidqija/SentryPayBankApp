package com.example.sentrypaybank.navigation

import androidx.navigation.navigation


sealed class NavBar(val route : String){
    object Signin : NavBar("signin")
    object HomePage : NavBar("home")

}