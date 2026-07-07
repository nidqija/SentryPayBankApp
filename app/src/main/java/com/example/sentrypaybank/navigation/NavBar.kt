package com.example.sentrypaybank.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation



sealed class NavBar(val route : String){
    object Signin : NavBar("signin")
    object HomePage : NavBar("home")

    object PipelinePage : NavBar("pipeline")

    object TransactionPage : NavBar("transaction")

}

sealed class BottomBarScreen(val route: String , val label : String){
    object Home : BottomBarScreen("home_tab", "Home")
    object Pipelines : BottomBarScreen("pipelines_tab", "Pipelines")
    object Payment : BottomBarScreen("payment_tab", "Payment")
    object Profile : BottomBarScreen("profile_tab", "Profile")

    object Transaction : BottomBarScreen("transaction_tab" , "Transaction")


}


