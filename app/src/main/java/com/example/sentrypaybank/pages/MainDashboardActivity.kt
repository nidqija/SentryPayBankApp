package com.example.sentrypaybank.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sentrypaybank.R
import com.example.sentrypaybank.backend.remote.data.repository.AuthRepository
import com.example.sentrypaybank.backend.remote.data.repository.TransactionRepository
import com.example.sentrypaybank.navigation.BottomBarScreen
import com.example.sentrypaybank.backend.remote.data.viewmodel.MainViewModel
import com.example.sentrypaybank.backend.remote.data.viewmodel.TransactionLayerModel
import com.example.sentrypaybank.navigation.BottomBarScreen.Home
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.math.log


@Composable
fun MainDashboardActivity(viewModel: MainViewModel? = null){
    val nestedNavController = rememberNavController()

    // create a mutable state val variable for repository
    val repository = remember { AuthRepository() }
    val transactionRepository = remember { TransactionRepository() }

    // parse the var to transactionviewmodel , as the model view uses authrepository to fetch data
    val transactionViewModel : TransactionLayerModel = viewModel (
        factory = viewModelFactory {
            initializer {
                TransactionLayerModel(authRepository = repository , transactionRepository = transactionRepository)
            }
        }
    )

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
                HomeActivity(
                    viewModel = viewModel,
                    onNavigatetoSubscriptionDetails = {
                        selectedId ->
                        nestedNavController.navigate("subscription-details/${selectedId}")
                    }
                )
            }
            composable(BottomBarScreen.Pipelines.route){
                PipelineActivity(
                    onNavigateToPipelineById = { selectedServiceId ->
                        nestedNavController.navigate("service-details/$selectedServiceId")
                    }
                )
            }
            composable(BottomBarScreen.Payment.route){
                // parse in as usual
                TransactionActivity(
                    viewModel = transactionViewModel,
                    // nested controller to redirect to another sub api
                    onContactSelected = {contactUser ->
                        nestedNavController.navigate("user_transaction/${contactUser.id}/${contactUser.fullName}/${contactUser.phoneNumber}/${contactUser.username}")
                    },
                    onNavigateToRecentTransaction = {
                        val now = System.currentTimeMillis()
                        val hourInMillis = 3600000L
                        val dayInMillis = 86400000L

                        val mockTransactions = listOf(
                            RecentTransaction(
                                transactionId = "TXN001",
                                senderId = "AMZ-MARKETPLACE",
                                receiverId = "USER_123",
                                amount = BigDecimal("150.00"),
                                createdAt = LocalDateTime.now().minusHours(2)
                            ),
                            RecentTransaction(
                                transactionId = "TXN002",
                                senderId = "USER_123",
                                receiverId = "PIPELINE-RENEWAL",
                                amount = BigDecimal("24.99"),
                                createdAt = LocalDateTime.now().minusDays(1)
                            )
                        )

                        nestedNavController.navigate("recent_transactions")


                    }
                )
            }


            composable(
                "user_transaction/{userId}/{fullName}/{phoneNumber}/{username}"
            ){
                backStackEntry ->
                val receiverId = backStackEntry.arguments?.getString("userId").orEmpty()
                val fullName = backStackEntry.arguments?.getString("fullName").orEmpty()
                val phoneNumber = backStackEntry.arguments?.getString("phoneNumber").orEmpty()
                val username = backStackEntry.arguments?.getString("username").orEmpty()


                val currentSenderId = viewModel?.userId ?:1L

                UserTransactionActivity(

                    // pass the id according to its data types
                    senderId = currentSenderId,
                    fullName = fullName,
                    phoneNumber = phoneNumber,
                    receiverId = receiverId,
                    userName = username
                )
            }
            composable(BottomBarScreen.Profile.route){
                HomeActivity(viewModel = viewModel)
            }

            // define the custom route to be called by other functions
            composable("recent_transactions"){

                // fetch the logged-in user id from main state model
                val loggedInUserId = viewModel?.userId ?: 1L
                // create a mock data
                val now = System.currentTimeMillis()
                val hourInMillis = 3600000L
                val dayInMillis = 86400000L

                // include a mock list
                val mockTransactions = listOf(
                    RecentTransaction(
                        transactionId = "TXN001",
                        senderId = "AMZ-MARKETPLACE",
                        receiverId = "USER_123",
                        amount = BigDecimal("150.00"),
                        createdAt = LocalDateTime.now().minusHours(2)
                    ),

                    RecentTransaction(
                        transactionId = "TXN002",
                        senderId = "USER_123",
                        receiverId = "PIPELINE-RENEWAL",
                        amount = BigDecimal("24.99"),
                        createdAt = LocalDateTime.now().minusDays(1)
                    )
                )
                // instantiate the object based on the desired arguments defined in the recent transaction frontend page
                RecentTransactionActivity(
                    viewModel = transactionViewModel,
                    userId = loggedInUserId
                )
            }

            composable("subscription-details/{subscriptionId}"){
                backStackEntry ->
                val targetId = backStackEntry.arguments?.getString("subscriptionId").orEmpty()

                ViewPipeLineActivity(
                    subscriptionId = targetId,
                    viewModel = viewModel,
                    onBackClick = {
                        nestedNavController.navigate(Home.route){
                            popUpTo(Home.route){inclusive= true}
                        }
                    }
                )

            }

            composable("service-details/{subscriptionId}"){
                backStackEntry ->
                val targetId = backStackEntry.arguments?.getString("serviceId").orEmpty()

                ViewPipelinetoSubscribeActivity()


            }









        }
    }
}



