package com.example.sentrypaybank.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sentrypaybank.R
import com.example.sentrypaybank.backend.remote.data.viewmodel.TransactionLayerModel
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class RecentTransaction(
    val transactionId: String,
    val senderId: String,
    val receiverId: String,
    val amount: BigDecimal,
    val createdAt: LocalDateTime
)

@Composable
fun RecentTransactionActivity(
    modifier: Modifier = Modifier,
    currentUserId: String? = null, // Changed to nullable to use userId if not provided
    transactions: List<RecentTransaction> = emptyList(),
    userId : Long,
    viewModel: TransactionLayerModel = viewModel(),
    ) {

    val currentUserIdString = currentUserId ?: userId.toString()

    val historyResponseState = viewModel.userTransactionHistory.collectAsStateWithLifecycle()
    val historyResponse = historyResponseState.value
    val errorState by viewModel.errorState.collectAsStateWithLifecycle()

    val displayTransactions = remember(historyResponse, transactions) {
        if (transactions.isNotEmpty()) {
            transactions
        } else {
            historyResponse.map { detail ->
                RecentTransaction(
                    transactionId = detail.transactionId,
                    senderId = detail.senderId,
                    receiverId = detail.receiverId,
                    amount = detail.amount,
                    createdAt = try {
                        LocalDateTime.parse(detail.createdAt)
                    } catch (e: Exception) {
                        LocalDateTime.now() // Fallback
                    }
                )
            }
        }
    }



    // Style alignments pulled directly from your theme profile
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

    LaunchedEffect(userId) {
            viewModel.fetchTransactionHistory(userId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gxBankBackgroundGradient)
            .padding(horizontal = 16.dp)
    ) {
        // Header Structure
        Column(
            modifier = Modifier.padding(top = 24.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
        ) {
            Text(
                text = "Transaction History",
                color = Color.White,
                fontFamily = IBMPlexSansFontFamily,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Your recent incoming and outgoing bank logs",
                color = Color.White.copy(alpha = 0.5f),
                fontFamily = IBMPlexSansFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            )
        }

        if (errorState != null) {
            Text(
                text = "Error: $errorState",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (displayTransactions.isEmpty()) {
            // Empty State Handling
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (errorState == null) "No recent transactions found." else "Failed to load transactions.",
                    color = Color.White.copy(alpha = 0.4f),
                    fontFamily = IBMPlexSansFontFamily,
                    fontSize = 16.sp
                )
            }
        } else {
            // Transaction Feed Log
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(displayTransactions) { transaction ->
                    val isIncoming = transaction.receiverId.trim().equals(currentUserIdString.trim(), ignoreCase = true)
                    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
                    val formattedDate = transaction.createdAt.format(dateFormatter)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(16.dp))
                            .background(navBarDarkBackground.copy(alpha = 0.6f))
                            .background(cardBackground)
                            .border(
                                width = 1.2.dp,
                                brush = Brush.linearGradient(
                                    listOf(Color.White.copy(alpha = 0.12f), Color.Transparent)
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Left Side: Direction Indicator Badge & Party Info
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                // Mini status indicator dot
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(
                                            color = if (isIncoming){
                                                neonGreenAccent
                                            } else {
                                                Color.White.copy(alpha = 0.4f)
                                            },
                                            shape = CircleShape
                                        )
                                )

                                Spacer(modifier = Modifier.width(14.dp))

                                Column {
                                    Text(
                                        text = if (isIncoming){
                                            "Received from ${transaction.senderId}"
                                        } else {
                                            "Sent to ${transaction.receiverId}"
                                        },
                                        color = Color.White,
                                        fontFamily = IBMPlexSansFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 15.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = formattedDate,
                                        color = Color.White.copy(alpha = 0.4f),
                                        fontFamily = IBMPlexSansFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            // Right Side: Signed Amount readouts
                            Text(
                                text = if (isIncoming) {
                                    "+$${transaction.amount}"
                                } else {
                                    "-$${transaction.amount}"
                                },
                                color = if (isIncoming){
                                    neonGreenAccent
                                } else {
                                    Color.White
                                },
                                fontFamily = IBMPlexSansFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecentTransitionPreview() {
    // Generate functional dummy values to make the Compose Preview render accurately
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
        ),
        RecentTransaction(
            transactionId = "TXN003",
            senderId = "JENNIFER_LEE",
            receiverId = "USER_123",
            amount = BigDecimal("800.00"),
            createdAt = LocalDateTime.now().minusDays(3).minusHours(4)
        ),
        RecentTransaction(
            transactionId = "TXN004",
            senderId = "USER_123",
            receiverId = "KROGER-STORES",
            amount = BigDecimal("64.12"),
            createdAt = LocalDateTime.now().minusDays(5)
        )
    )

    SentryPayBankTheme {
        RecentTransactionActivity(
            currentUserId = "USER_123",
            transactions = mockTransactions,
            userId = 1L
        )
    }
}
