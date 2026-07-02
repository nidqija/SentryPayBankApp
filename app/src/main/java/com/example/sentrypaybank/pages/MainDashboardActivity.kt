package com.example.sentrypaybank.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sentrypaybank.R
import com.example.sentrypaybank.navigation.BottomBarScreen
import com.example.sentrypaybank.backend.remote.data.viewmodel.MainViewModel


@Composable
fun MainDashboardActivity(viewModel: MainViewModel? = null){
    val nestedNavController = rememberNavController()

    val navItems = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Pipelines,
        BottomBarScreen.Profile,
        BottomBarScreen.Payment
    )

    val IBMPlexSansFontFamily = FontFamily(
        Font(resId = R.font.ibmplexsans_medium, weight = FontWeight.Medium),
        Font(resId = R.font.ibmplexsans_regular, weight = FontWeight.Normal),
        Font(resId = R.font.ibmplexsans_semibold, weight = FontWeight.SemiBold)
    )

    val neonGreenAccent = Color(0xFF00E676)
    val cardBackground = Color(0xFF1F2937).copy(alpha = 0.4f)
    val navBarDarkBackground = Color(0xFF0B0F19) // Balanced premium dark mode backdrop match
    val gxBankBackgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0B0F19),
            Color(0xFF111827),
            Color(0xFF1F2937)
        )
    )

    Scaffold(bottomBar = {
        NavigationBar(
            containerColor = navBarDarkBackground,
            tonalElevation = 8.dp,
            modifier = Modifier.border(1.dp, Color.White.copy(alpha = 0.05f) , RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {

            val navBackStackEntry by nestedNavController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            navItems.forEach { screen ->
                NavigationBarItem(
                    selected = currentRoute == screen.route,
                    onClick = {
                        if (currentRoute != screen.route){
                            nestedNavController.navigate(screen.route){
                                popUpTo(nestedNavController.graph.findStartDestination().id ){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    label = { Text(text = screen.label, fontFamily = IBMPlexSansFontFamily, fontSize = 12.sp) },
                    icon = { /* Add icons here */ },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0B0F19),
                        selectedTextColor = Color(0xFF00E676),
                        indicatorColor = Color(0xFF00E676),
                        unselectedTextColor = Color.White.copy(alpha = 0.4f)
                    )
                )
            }
        }
    }
    ){
        innerPadding ->
        NavHost(
            navController = nestedNavController,
            startDestination = BottomBarScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){

            composable(BottomBarScreen.Home.route){
                HomeActivity(viewModel = viewModel)
            }
            composable(BottomBarScreen.Pipelines.route){
                PipelineActivity()
            }
            composable(BottomBarScreen.Payment.route){
                HomeActivity(viewModel = viewModel)
            }
            composable(BottomBarScreen.Profile.route){
                HomeActivity(viewModel = viewModel)
            }


        }
    }
}