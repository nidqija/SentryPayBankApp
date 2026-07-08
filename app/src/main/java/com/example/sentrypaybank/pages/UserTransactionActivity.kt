package com.example.sentrypaybank.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sentrypaybank.R
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme

@Composable
fun UserTransactionActivity(
    modifier: Modifier = Modifier,
    userId: String,
    fullName: String,
    userName : String,
    phoneNumber: String,
    onTransferClick: (amount: String) -> Unit = {}
) {
    // Exact design tokens from TransactionActivity
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

    var amountQuery by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gxBankBackgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- TOP HEADER ---
            Text(
                text = "Send Money",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontFamily = IBMPlexSansFontFamily,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- RECIPIENT CARD ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardBackground, shape = RoundedCornerShape(16.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar Circle
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(neonGreenAccent.copy(alpha = 0.15f), shape = CircleShape)
                        .border(1.dp, neonGreenAccent.copy(alpha = 0.4f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = fullName.take(1).uppercase(),
                        color = neonGreenAccent,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        fontFamily = IBMPlexSansFontFamily
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Name & Phone details
                Column {
                    Text(
                        text = fullName,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = IBMPlexSansFontFamily
                    )
                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = userName,
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = IBMPlexSansFontFamily
                    )

                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = phoneNumber,
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 13.sp,
                        fontFamily = IBMPlexSansFontFamily
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // --- AMOUNT INPUT FIELD ---
            Text(
                text = "Enter Amount",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.5f),
                fontFamily = IBMPlexSansFontFamily
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = amountQuery,
                onValueChange = { input ->
                    // Guard input logic so it only accepts numbers/decimals
                    if (input.all { it.isDigit() || it == '.' }) amountQuery = input
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontFamily = IBMPlexSansFontFamily,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                ),
                prefix = {
                    Text(
                        text = "$",
                        color = neonGreenAccent,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = IBMPlexSansFontFamily
                    )
                },
                placeholder = {
                    Text(
                        text = "0.00",
                        color = Color.White.copy(alpha = 0.2f),
                        fontFamily = IBMPlexSansFontFamily,
                        fontSize = 32.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = neonGreenAccent,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                )
            )

            Spacer(modifier = Modifier.weight(1f)) // Pushes the button perfectly down to the bottom

            // --- TRANSFER BUTTON ---
            Button(
                onClick = { if (amountQuery.isNotEmpty()) onTransferClick(amountQuery) },
                enabled = amountQuery.isNotEmpty() && amountQuery.toDoubleOrNull() ?: 0.0 > 0.0,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonGreenAccent,
                    contentColor = Color(0xFF0B0F19),
                    disabledContainerColor = Color.White.copy(alpha = 0.1f),
                    disabledContentColor = Color.White.copy(alpha = 0.3f)
                )
            ) {
                Text(
                    text = "Confirm & Transfer",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = IBMPlexSansFontFamily
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserTransactionActivityPreview() {
    SentryPayBankTheme {
        UserTransactionActivity(
            userId = "12345",
            fullName = "John Doe",
            userName = "johnthepork",
            phoneNumber = "+1234567890"
        )
    }
}
