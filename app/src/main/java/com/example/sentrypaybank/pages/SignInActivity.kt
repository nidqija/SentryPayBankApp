package com.example.sentrypaybank.pages

import com.example.sentrypaybank.backend.remote.data.repository.AuthRepository
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sentrypaybank.R
import com.example.sentrypaybank.components.AntiPhishingForm
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme
import kotlinx.coroutines.launch

@Composable
fun SignInActivity(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
    onNavigateToSignIn : () -> Unit = {},
    repository: AuthRepository = remember { AuthRepository() },
    onSignInSubmit: (String) -> Unit = {}
) {
    // State management for user credentials and feedback UI states
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var showPhishingOverlay by remember { mutableStateOf(false) }
    var targetPhishingName by remember {mutableStateOf("")}

    val scope = rememberCoroutineScope()

    // Theme Accent Colors
    val neonGreenAccent = Color(0xFF00E676)
    val inputFieldTextColor = Color.White

    // GX Bank Vibe: Ultra-deep premium dark backdrop gradient
    val gxBankBackgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0B0F19), // Deepest dark slate/black
            Color(0xFF111827), // Midnight gray
            Color(0xFF1F2937)  // Subtle metallic dark gray base
        )
    )

    // Project custom font configurations matching Cover Page
    val IBMPlexSansFontFamily = FontFamily(
        Font(resId = R.font.ibmplexsans_medium, weight = FontWeight.Medium),
        Font(resId = R.font.ibmplexsans_regular, weight = FontWeight.Normal),
        Font(resId = R.font.ibmplexsans_semibold, weight = FontWeight.SemiBold)
    )


    Box(
        modifier = Modifier.fillMaxSize()
            .background(gxBankBackgroundGradient)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(gxBankBackgroundGradient)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Main Branding Header
            Text(
                text = "SENTRY PAY",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 6.sp,
                color = Color.White,
                fontFamily = IBMPlexSansFontFamily
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Branding Sub-heading
            Text(
                text = "Sign in to your digital bank account",
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                fontFamily = IBMPlexSansFontFamily
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Email Input Field
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = null // Clear error when user retypes
                },
                label = { Text("Email Address", fontFamily = IBMPlexSansFontFamily) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = inputFieldTextColor,
                    unfocusedTextColor = inputFieldTextColor,
                    focusedBorderColor = neonGreenAccent,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                    focusedLabelColor = neonGreenAccent,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.4f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input Field
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = null // Clear error when user retypes
                },
                label = { Text("Password", fontFamily = IBMPlexSansFontFamily) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = inputFieldTextColor,
                    unfocusedTextColor = inputFieldTextColor,
                    focusedBorderColor = neonGreenAccent,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                    focusedLabelColor = neonGreenAccent,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.4f)
                )
            )

            // ✨ Dynamic Error Box Rendering
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = errorMessage!!,
                    color = Color(0xFFFF5252), // Clean premium material design red tint
                    fontSize = 13.sp,
                    fontFamily = IBMPlexSansFontFamily,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Primary Action: Premium Neon Sign In Button
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        isLoading = true
                        errorMessage = null
                        scope.launch {
                            val result = repository.loginUser(email, password)
                            isLoading = false


                            if (result.isSuccess) {
                                val loginData = result.getOrNull()

                                if(loginData != null){
                                    targetPhishingName = loginData.antiPhishingName
                                    onSignInSubmit(loginData.token)
                                    showPhishingOverlay = true
                                }


                            } else {
                                errorMessage = result.exceptionOrNull()?.message
                                    ?: "An unexpected error occurred"
                            }
                        }
                    } else {
                        errorMessage = "Please enter both an email address and a password."
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading, // Block spam taps while network simulation delays
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonGreenAccent,
                    contentColor = Color(0xFF0B0F19)
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color(0xFF0B0F19),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Sign In",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = IBMPlexSansFontFamily
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonGreenAccent,
                    contentColor = Color(0xFF0B0F19),
                )

            ) {
                Text(
                    text = "Forgot password ? contact us",
                    fontWeight = FontWeight.Normal,
                    fontFamily = IBMPlexSansFontFamily

                )
            }
        }
        if (showPhishingOverlay) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.75f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White)


                ) {
                    AntiPhishingForm(
                        // for a component , define the function and its implementation here
                        // it calls the function defined in component, and it will function as usual
                        // ex : to redirect to home page , define a val at the form and implement it via
                        // a constructor
                        // pass the state param to the phishing form param
                        phishingName = targetPhishingName,
                        onNavigateToHome = {
                            showPhishingOverlay = false
                            onNavigateToHome()

                        },
                        onNavigateToSignIn = {
                            showPhishingOverlay = false
                            email = ""
                            password = ""
                            onNavigateToSignIn()
                        }



                    )
                }
            }
        }
    }



}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SentryPayBankTheme {
        SignInActivity()
    }
}