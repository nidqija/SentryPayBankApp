package com.example.sentrypaybank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sentrypaybank.navigation.NavBar
import com.example.sentrypaybank.pages.HomeActivity
import com.example.sentrypaybank.pages.SignInActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            SentryPayBankTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SetryPayAppNavigation (
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }


        }
    }
}



@Composable
fun SetryPayAppNavigation(modifier: Modifier = Modifier){
    var navController = rememberNavController()


    NavHost(
       navController = navController,
        startDestination = NavBar.Signin.route,
        modifier = modifier

    ) {
        composable(NavBar.Signin.route){
            SignInActivity(
                onNavigateToHome = {
                    navController.navigate(NavBar.HomePage.route)

                }
            )
        }


        composable (NavBar.HomePage.route){
            HomeActivity()
        }
    }
}