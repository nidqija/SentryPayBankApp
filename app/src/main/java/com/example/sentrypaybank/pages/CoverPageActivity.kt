package com.example.sentrypaybank.pages

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
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme
import kotlinx.coroutines.delay
import com.example.sentrypaybank.R

@Composable
fun CoverPageActivity(onNavigatetoSignIn: () -> Unit) {
    // Automatically redirect after 2.5 seconds

    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(5000)
        onNavigatetoSignIn()
    }

    // GX Bank Vibe: Ultra-deep premium dark backdrop gradient
    val gxBankBackgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0B0F19), // Deepest dark slate/black
            Color(0xFF111827), // Midnight gray
            Color(0xFF1F2937)  // Subtle metallic dark gray base
        )
    )

    // Keep the font as requested, matching your resource naming
    val IBMPlexSansFontFamily = FontFamily(
        Font(resId = R.font.ibmplexsans_medium , weight = FontWeight.Medium),
        Font(resId = R.font.ibmplexsans_regular , weight = FontWeight.Normal),
        Font(resId = R.font.ibmplexsans_semibold , weight = FontWeight.SemiBold)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gxBankBackgroundGradient),
        contentAlignment = Alignment.Center
    ) {

        AnimatedVisibility(
            visible = startAnimation,
            enter = fadeIn(animationSpec = tween(durationMillis = 1500))
        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 45.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sentrypay_logo),
                contentDescription = "Sentry Pay Logo",
                modifier = Modifier


            )

            Spacer(modifier = Modifier.height(4.dp))
            // Main Branding Text with a hint of GX Neon styling
            Text(
                text = "SENTRY PAY",
                fontSize = 38.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 8.sp, // Enhanced letter spacing for tech-first look
                color = Color.White,
                fontFamily = IBMPlexSansFontFamily
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Minimalist Subtitle incorporating the signature GX Neon Teal/Green accent color
            Text(
                text = "Secure. Instant. Reliable.",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 3.sp,
                color = Color(0xFF00E676), // GX Neon Green accent pop color
                textAlign = TextAlign.Center,
                fontFamily = IBMPlexSansFontFamily
            )
        }
        }

        AnimatedVisibility(

            visible = startAnimation,
            enter = fadeIn(animationSpec = tween (durationMillis = 1500)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
        // Brand legal or tagline pinned subtly at the very bottom
        Text(
            text = "Digital Banking Experience",
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White.copy(alpha = 0.3f),
            letterSpacing = 1.5.sp,
            fontFamily = IBMPlexSansFontFamily,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoverPageScreenActivity(){
    SentryPayBankTheme {
        CoverPageActivity({})
    }
}