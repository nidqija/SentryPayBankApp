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
import com.example.sentrypaybank.pages.CoverPageActivity
import com.example.sentrypaybank.pages.HomeActivity
import com.example.sentrypaybank.pages.SignInActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enable edge to edge display , allowing content to draw behind the system bars
        enableEdgeToEdge()

        // define ui content using jetpack compose
        setContent {
            // applies application global material 3 styling theme
            SentryPayBankTheme {
                // scaffold provides a standard layout structure
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // main app nav graph , pass inner paddding to respect edge to edge inspect
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

    // keep track of the navigation stack and current screen state
    var navController = rememberNavController()


    // connects the navcontroller to the nav graph destinations
    NavHost(
       navController = navController,
        startDestination = "cover_page",
        modifier = modifier

    ) {
        // destination route for sign in screen
        composable("cover_page"){
            CoverPageActivity(
                onNavigatetoSignIn = {
                    navController.navigate(NavBar.Signin.route){
                        popUpTo("cover_page"){inclusive = true}
                    }
                }
            )
        }

        composable(NavBar.Signin.route) { // Ensure this matches what navController calls!
            SignInActivity(
                onNavigateToHome = {
                    navController.navigate(NavBar.HomePage.route)
                }
            )
        }


        // destination route for home page
        composable (NavBar.HomePage.route){
            HomeActivity (
                // define the function of the navigation to sign in page
                onNavigateToSignIn = {
                    navController.navigate(NavBar.Signin.route){
                        popUpTo(NavBar.HomePage.route){inclusive=true}
                    }
                }


            )
        }


    }
}