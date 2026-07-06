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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsProperties.InputText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sentrypaybank.R
import com.example.sentrypaybank.backend.remote.data.viewmodel.MainViewModel
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme
import com.google.gson.annotations.SerializedName
import java.util.Locale


@Composable
fun TransactionActivity(
    modifier : Modifier = Modifier

){


    val neonGreenAccent = Color(0xFF00E676)
    val cardBackground = Color(0xFF1F2937).copy(alpha = 0.4f)
    val gxBankBackgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0B0F19),
            Color(0xFF111827),
            Color(0xFF1F2937)
        )
    )

    var searchQuery by remember { mutableStateOf("") }

    val IBMPlexSansFontFamily = FontFamily(
        Font(resId = R.font.ibmplexsans_medium, weight = FontWeight.Medium),
        Font(resId = R.font.ibmplexsans_regular, weight = FontWeight.Normal),
        Font(resId = R.font.ibmplexsans_semibold, weight = FontWeight.SemiBold)
    )






        Column(
            modifier = modifier
                .fillMaxSize()
                .background(gxBankBackgroundGradient)
                .padding(12.dp)
                .padding(horizontal = 24.dp)
        )
    {

            Column(
                modifier = Modifier
                    .padding(5.dp)

            ) {
                Text(
                    text = "Welcome Back",
                    fontSize = 20.sp,
                    color = Color.White.copy(alpha = 0.5f),
                    fontFamily = IBMPlexSansFontFamily,

                )

                Spacer(modifier = Modifier.height(12.dp))


                Text(
                  text = "Search for your contacts to perform transactions",
                    fontSize = 17.sp,
                    color = Color.White,
                    fontFamily = IBMPlexSansFontFamily,
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {searchQuery = it},
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.White,
                        fontFamily = IBMPlexSansFontFamily
                    ),

                    placeholder = {
                        Text(
                            text = "Enter phone , username or fullname",
                            color = Color.White.copy(alpha = 0.4f),
                            fontFamily = IBMPlexSansFontFamily,
                            fontSize = 15.sp

                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = neonGreenAccent,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                        focusedContainerColor = cardBackground,
                        unfocusedContainerColor = cardBackground,
                    )

                )







            }
    }



}



@Preview
@Composable
fun TransactionActivityPreview(){
    SentryPayBankTheme {
        TransactionActivity()
    }
}
