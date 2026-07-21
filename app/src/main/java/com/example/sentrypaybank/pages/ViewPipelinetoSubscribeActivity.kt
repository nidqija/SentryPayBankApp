package com.example.sentrypaybank.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sentrypaybank.R
import com.example.sentrypaybank.backend.remote.data.viewmodel.ServiceViewModel
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme

@Composable
fun ViewPipelinetoSubscribeActivity(
    modifier: Modifier = Modifier,
    // Replace with your actual item type if passed via navigation/ViewModel
    serviceName: String = "Sentry Shield Pro",
    serviceDesc: String = "Complete automated transaction monitoring, anomaly detection, and priority fraud resolution for high-frequency accounts.",
    servicePrice: Double = 14.99,
    currency: String = "USD",
    onConfirmSubscribe: (billingCycle: String) -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: ServiceViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    // Styling colors aligned with your PipelineActivity theme
    val neonGreenAccent = Color(0xFF00E676)
    val cardBackground = Color(0xFF1F2937).copy(alpha = 0.4f)
    val navBarDarkBackground = Color(0xFF0B0F19)
    val gxBankBackgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0B0F19),
            Color(0xFF111827),
            Color(0xFF1F2937)
        )
    )

    val IBMPlexSansFontFamily = FontFamily(
        Font(resId = R.font.ibmplexsans_medium, weight = FontWeight.Medium),
        Font(resId = R.font.ibmplexsans_regular, weight = FontWeight.Normal),
        Font(resId = R.font.ibmplexsans_semibold, weight = FontWeight.SemiBold)
    )

    // State for selected billing cycle
    var selectedCycle by remember { mutableStateOf("Monthly") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gxBankBackgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Section
            Column(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = "Subscribe to Service",
                    color = Color.White,
                    fontFamily = IBMPlexSansFontFamily,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Review your details and select a subscription plan below.",
                    color = Color.White.copy(alpha = 0.5f),
                    fontFamily = IBMPlexSansFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Service Summary Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(navBarDarkBackground.copy(alpha = 0.6f))
                    .background(cardBackground)
                    .border(
                        width = 1.2.dp,
                        brush = Brush.linearGradient(
                            listOf(Color.White.copy(alpha = 0.15f), Color.Transparent)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = serviceName,
                        color = Color.White,
                        fontFamily = IBMPlexSansFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = serviceDesc,
                        color = Color.White.copy(alpha = 0.65f),
                        fontFamily = IBMPlexSansFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Base Price",
                            color = Color.White.copy(alpha = 0.7f),
                            fontFamily = IBMPlexSansFontFamily,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "$servicePrice $currency",
                            color = neonGreenAccent,
                            fontFamily = IBMPlexSansFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Select Billing Plan Section
            Text(
                text = "Select Billing Cycle",
                color = Color.White,
                fontFamily = IBMPlexSansFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Monthly Option
            BillingOptionCard(
                title = "Monthly Billing",
                subtitle = "Billed every month. Cancel anytime.",
                priceText = "$servicePrice $currency / mo",
                isSelected = selectedCycle == "Monthly",
                fontFamily = IBMPlexSansFontFamily,
                accentColor = neonGreenAccent,
                onClick = { selectedCycle = "Monthly" }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Annual Option (with discount highlight)
            val annualPrice = String.format("%.2f", servicePrice * 10) // 2 months free equivalent
            BillingOptionCard(
                title = "Annual Billing",
                subtitle = "Save 16% — Billed yearly",
                priceText = "$annualPrice $currency / yr",
                isSelected = selectedCycle == "Annual",
                fontFamily = IBMPlexSansFontFamily,
                accentColor = neonGreenAccent,
                onClick = { selectedCycle = "Annual" }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Subscribe Button
            Button(
                onClick = { onConfirmSubscribe(selectedCycle) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonGreenAccent,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Confirm Subscription",
                    fontFamily = IBMPlexSansFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Back/Cancel Button
            TextButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Cancel",
                    color = Color.White.copy(alpha = 0.6f),
                    fontFamily = IBMPlexSansFontFamily,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun BillingOptionCard(
    title: String,
    subtitle: String,
    priceText: String,
    isSelected: Boolean,
    fontFamily: FontFamily,
    accentColor: Color,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) accentColor else Color.White.copy(alpha = 0.15f)
    val backgroundAlpha = if (isSelected) 0.12f else 0.05f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = backgroundAlpha))
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    color = Color.White.copy(alpha = 0.5f),
                    fontFamily = fontFamily,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = priceText,
                color = if (isSelected) accentColor else Color.White.copy(alpha = 0.8f),
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewPipelinetoSubscribePreview() {
    SentryPayBankTheme {
        ViewPipelinetoSubscribeActivity()
    }
}