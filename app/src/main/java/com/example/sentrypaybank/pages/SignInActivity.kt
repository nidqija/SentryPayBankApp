package com.example.sentrypaybank.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme

@Composable
fun SignInActivity(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
    onSignInSubmit: () -> Unit = {}
) {
    // State management for user credentials
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Theme Accent Colors
    val neonGreenAccent = Color(0xFF00E676)
    val inputFieldTextColor = Color.White

    // GX Bank Vibe: Ultra-deep premium dark backdrop gradient matching Cover Page
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gxBankBackgroundGradient)
            .padding(horizontal = 32.dp), // Matched padding behavior for structured alignment
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Main Branding Header styled identically to Cover Page
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

        // Email Input Field matching dark mode palette
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address", fontFamily = IBMPlexSansFontFamily) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,

            // define extra styling on the text box using OutlinedTextFieldDefaults
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

        // Password Input Field matching dark mode palette
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", fontFamily = IBMPlexSansFontFamily) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
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

        Spacer(modifier = Modifier.height(32.dp))

        // Primary Action: Premium Neon Sign In Button
        Button(
            onClick = { onSignInSubmit() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = neonGreenAccent,
                contentColor = Color(0xFF0B0F19) // Dark text contrast against neon background
            )
        ) {
            Text(
                text = "Sign In",
                fontWeight = FontWeight.SemiBold,
                fontFamily = IBMPlexSansFontFamily
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Secondary Action: Minimalist Back to Home Button
        OutlinedButton(
            onClick = { onNavigateToHome() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
        ) {
            Text(
                text = "Go back to home",
                fontWeight = FontWeight.Normal,
                fontFamily = IBMPlexSansFontFamily
            )
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