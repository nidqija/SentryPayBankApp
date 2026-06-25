package com.example.sentrypaybank.pages

import java.util.Locale
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sentrypaybank.R
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme

data class SubscriptionItem(
    val id: String,
    val name: String,
    val category: String,
    val cost: Double,
    val renewalDate: String,
    val initialLetter: String
)

@Composable
fun HomeActivity(
    modifier: Modifier = Modifier,
    onNavigateToSignIn: () -> Unit = {}
) {
    val neonGreenAccent = Color(0xFF00E676)
    val cardBackground = Color(0xFF1F2937).copy(alpha = 0.4f)

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

    val subscriptions = remember {
        listOf(
            SubscriptionItem("1", "GitHub Copilot", "Developer Tools", 10.00, "July 12, 2026", "G"),
            SubscriptionItem("2", "Netflix Premium", "Entertainment", 19.99, "July 18, 2026", "N"),
            SubscriptionItem("3", "AWS Cloud Compute", "Infrastructure", 142.50, "July 01, 2026", "A"),
            SubscriptionItem("4", "Spotify Family", "Entertainment", 16.99, "July 24, 2026", "S"),
            SubscriptionItem("5", "Figma Pro", "Design Tools", 15.00, "August 02, 2026", "F")
        )
    }

    val totalMonthlySpend = remember(subscriptions) { subscriptions.sumOf { it.cost } }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gxBankBackgroundGradient)
            .padding(horizontal = 24.dp)
    ) {
        // --- TOP TOOLBAR SECTION ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Welcome Back",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.5f),
                    fontFamily = IBMPlexSansFontFamily
                )
                Text(
                    text = "Sentry User",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontFamily = IBMPlexSansFontFamily
                )
            }

            // Space reserved for an optional profile icon/avatar badge later
        }

        // --- ANALYTICS CARD SECTION (Moved outside the Row) ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackground),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Monthly Spending",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.5.sp,
                    color = neonGreenAccent, // Swapped to accent green for fintech look
                    fontFamily = IBMPlexSansFontFamily
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 4.dp),
                        fontFamily = IBMPlexSansFontFamily
                    )
                    Text(
                        text = String.format(Locale.US, "%.2f", totalMonthlySpend),
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = IBMPlexSansFontFamily
                    )
                }

                // Fixed: Moved outside the currency Row into the parent Column hierarchy
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${subscriptions.size} Active digital pipeline linkings monitored.",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.5f),
                    fontFamily = IBMPlexSansFontFamily
                )
            }
        }

        Text(
            text = "Active Pipelines & Mandates",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            fontFamily = IBMPlexSansFontFamily,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)

        ){
            items(subscriptions) {subscription ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(cardBackground, RoundedCornerShape(14.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(14.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White.copy(alpha = 0.07f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = subscription.initialLetter,
                            color = neonGreenAccent,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = IBMPlexSansFontFamily
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))


                    Column(modifier = Modifier.weight(1f)){
                        Text(
                            text = subscription.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontFamily = IBMPlexSansFontFamily
                        )

                        Text(
                            text = "Renew ${subscription.renewalDate}",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.4f),
                            fontFamily = IBMPlexSansFontFamily
                        )
                    }

                    Column(horizontalAlignment = Alignment.End){
                        Text(
                            text = String.format(Locale.US, "-$%.2f", subscription.cost),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontFamily = IBMPlexSansFontFamily
                        )

                        Text(
                            text = "Auto",
                            fontSize = 11.sp,
                            color = neonGreenAccent,
                            fontFamily = IBMPlexSansFontFamily
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeActivityPreview() {
    SentryPayBankTheme {
        HomeActivity()
    }
}