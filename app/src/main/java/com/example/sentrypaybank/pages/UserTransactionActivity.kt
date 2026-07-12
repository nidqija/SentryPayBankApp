package com.example.sentrypaybank.pages

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.sentrypaybank.R
import com.example.sentrypaybank.backend.remote.data.repository.TransactionRepository
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme
import kotlinx.coroutines.launch

@Composable
fun UserTransactionActivity(
    modifier: Modifier = Modifier,
    receiverId: String,
    fullName: String,
    userName : String,
    phoneNumber: String,
    senderId : Long,
    repository: TransactionRepository = remember { TransactionRepository() },
    onTransferClick: (amount: String) -> Unit = {},
    onTransactionSuccess: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}

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
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isSuccessOpen by remember {mutableStateOf(false)}
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
                onClick = {
                    val amountDouble = amountQuery.toDoubleOrNull() ?: 0.0
                    if (amountQuery.isNotEmpty() && amountDouble > 0.0) {
                        isLoading = true
                        errorMessage = null

                        scope.launch {
                            val result = repository.postTransactionToUser(
                                senderId = senderId,
                                receiverId = receiverId,
                                amount = amountDouble
                            )



                            isLoading = false

                            if (result.isSuccess) {
                                Toast.makeText(context, "Transfer Successful!", Toast.LENGTH_SHORT)
                                    .show()
                                amountQuery = ""
                                onTransactionSuccess()
                                isSuccessOpen = true
                            } else {
                                errorMessage = result.exceptionOrNull()?.message
                                    ?: "An unexpected network error occurred"
                            }
                        }
                    }
                },
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

        if(isLoading){
            Dialog(
                onDismissRequest = {},
                properties = DialogProperties(dismissOnBackPress = false , dismissOnClickOutside = false)
            ){
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color(0xFF1F2937), shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator(color = neonGreenAccent)
                }
            }

        }

        if(isSuccessOpen){
            AlertDialog(
                onDismissRequest = {},
                containerColor = Color(0xFF1F2937),
                title = {
                    Text(
                        text = "Transaction Complete",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = IBMPlexSansFontFamily
                    )
                },
                text = {
                    Text(
                        text = "Your money has been successfully transferred to $fullName.",
                        color = Color.White.copy(alpha = 0.7f),
                        fontFamily = IBMPlexSansFontFamily
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            isSuccessOpen = false
                            onNavigateToHome()
                        }
                    ){
                        Text(
                            text = "Done",
                            color = neonGreenAccent,
                            fontWeight = FontWeight.Bold,
                            fontFamily = IBMPlexSansFontFamily
                        )
                    }
                }

            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun UserTransactionActivityPreview() {
    SentryPayBankTheme {
        UserTransactionActivity(
            senderId = 1L,
            fullName = "John Doe",
            userName = "johnthepork",
            phoneNumber = "+1234567890",
            receiverId = "12345"
        )
    }
}

