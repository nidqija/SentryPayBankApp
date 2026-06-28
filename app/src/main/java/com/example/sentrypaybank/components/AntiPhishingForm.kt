package com.example.sentrypaybank.components

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme
import kotlinx.coroutines.delay
import com.example.sentrypaybank.R
import com.example.sentrypaybank.backend.remote.data.LoginRequest


@Composable
fun AntiPhishingForm(
    modifier : Modifier = Modifier,
    onDisplayForm: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}

    ){

    var antiPhishingName by remember { mutableStateOf("") }
    val neonGreenAccent = Color(0xFF00E676)
    val inputFieldTextColor = Color(0xFF0F172A)

    val IBMPlexSansFontFamily = FontFamily(
        Font(resId = R.font.ibmplexsans_medium, weight = FontWeight.Medium),
        Font(resId = R.font.ibmplexsans_regular, weight = FontWeight.Normal),
        Font(resId = R.font.ibmplexsans_semibold, weight = FontWeight.SemiBold)
    )




    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Security Verification" ,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Confirm your anti-phishing identity phrase to safely verify this active session.",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )


        Text(
            text = "Are you antiPhishingName?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.padding(12.dp))

        Column(
            modifier = modifier.fillMaxWidth()
                .padding(5.dp)

        ){

                Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
                    .padding(12.dp)

            ) {
                Text(
                    text = "No"
                )
            }

            Button(
                onClick = {
                    onNavigateToHome()
                          },
                modifier = Modifier.fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = "Yes"
                )
            }

        }
    }
}

@Preview
@Composable
fun AntiPhishingFormPreview(){
    SentryPayBankTheme {
        AntiPhishingForm()
    }
}


