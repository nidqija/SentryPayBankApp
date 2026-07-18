
package com.example.sentrypaybank.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sentrypaybank.R
import com.example.sentrypaybank.backend.remote.data.viewmodel.MainViewModel
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme

@Composable
fun ViewPipeLineActivity(
    subscriptionId: String,
    viewModel: MainViewModel?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // --- DESIGN SYSTEM CORES ---
    val neonGreenAccent = Color(0xFF00E676)
    val cardBackground = Color(0xFF1F2937).copy(alpha = 0.4f)
    val errorRed = Color(0xFFFF5252)

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

    // --- STATE GRAB ---
    val userSubscriptionState = viewModel?.userSubscriptions?.collectAsStateWithLifecycle()
    val realSubscriptions = userSubscriptionState?.value?.serviceSubscriptions ?: emptyList()

    // Find the specific item passed via ID
    val subscription = realSubscriptions.find { (it.subscriptionId).toString() == subscriptionId }

    // Fallback if deep-linked or refreshed and item missing in cache
    LaunchedEffect(subscription) {
        if (subscription == null && viewModel != null) {
            viewModel.loggedInUserId.value?.let { userId ->
                viewModel.fetchUserSubscriptions(userId)
            }
        }
    }

    // Checking if running inside Android Studio Preview Layout
    val isPreview = LocalInspectionMode.current

    // Extracting structural layout fields directly to keep compiler happy
    val serviceName = subscription?.serviceName ?: if (isPreview) "GitHub Copilot" else ""
    val subscriptionType = subscription?.subscriptionType ?: if (isPreview) "Developer Infrastructure Tools" else ""
    val subscriptionStatus = subscription?.subscriptionStatus ?: if (isPreview) "active" else ""
    val endDate = subscription?.subscriptionEndDate?.toString() ?: if (isPreview) "July 12, 2026" else ""
    val initial = serviceName.take(1).uppercase()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gxBankBackgroundGradient)
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {

            // --- TEXT-ONLY HEADER TOOLBAR (No Icons) ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.05f), CircleShape)
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "‹",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        fontFamily = IBMPlexSansFontFamily
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Pipeline Blueprint",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontFamily = IBMPlexSansFontFamily
                )
            }

            // --- HERO CARD SECTION ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackground),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.White.copy(alpha = 0.07f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initial,
                            color = neonGreenAccent,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = IBMPlexSansFontFamily
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = serviceName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = IBMPlexSansFontFamily
                    )

                    Text(
                        text = subscriptionType,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.5f),
                        fontFamily = IBMPlexSansFontFamily,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // --- DATA FIELD SEGMENTS ---
            Text(
                text = "Mandate Parameters",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.4f),
                fontFamily = IBMPlexSansFontFamily,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackground),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.04f))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    PipelineDetailRow(
                        label = "Link Status",
                        value = subscriptionStatus.replaceFirstChar { it.uppercase() },
                        valueColor = neonGreenAccent,
                        fontFamily = IBMPlexSansFontFamily
                    )

                    PipelineDetailRow(
                        label = "Billing Sequence",
                        value = "Monthly Automated",
                        fontFamily = IBMPlexSansFontFamily
                    )

                    PipelineDetailRow(
                        label = "System End Date",
                        value = endDate,
                        fontFamily = IBMPlexSansFontFamily
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- ACTION PACK ---
            Button(
                onClick = { /* Action to update mapping or change limits */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(bottom = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = neonGreenAccent),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Modify Link Mandate",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    fontFamily = IBMPlexSansFontFamily
                )
            }

            OutlinedButton(
                onClick = { /* Action to kill pipeline connection */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = errorRed),
                border = BorderStroke(1.dp, errorRed.copy(alpha = 0.4f)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Sever Pipeline Connection",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    fontFamily = IBMPlexSansFontFamily
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PipelineDetailRow(
    label: String,
    value: String,
    fontFamily: FontFamily,
    valueColor: Color = Color.White
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.5f),
            fontFamily = fontFamily
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = valueColor,
            fontFamily = fontFamily
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ViewPipelinePreview(){
    SentryPayBankTheme{
        ViewPipeLineActivity(
            subscriptionId = "1",
            viewModel = null,
            onBackClick = {}
        )
    }
}

