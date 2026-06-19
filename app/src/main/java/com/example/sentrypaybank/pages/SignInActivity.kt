package com.example.sentrypaybank.pages


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme


@Composable
fun SignInActivity(

    modifier: Modifier = Modifier,
    onNavigateToHome:() -> Unit = {}

){
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable{onNavigateToHome()},
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "This is a sign in page",
            style = MaterialTheme.typography.headlineMedium

        )
    }


}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview(){
    SentryPayBankTheme() {
        SignInActivity()
    }
}