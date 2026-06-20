package com.example.sentrypaybank.pages


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme



@Composable
fun HomeActivity(
    modifier: Modifier = Modifier,
    onNavigateToSignIn : () -> Unit = {}

){

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable{onNavigateToSignIn()},
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "This is a home page",
            style = MaterialTheme.typography.headlineMedium

        )

        Spacer(modifier = Modifier.padding(20.dp))

        Button(onClick = {onNavigateToSignIn()} ,
            modifier = Modifier.fillMaxWidth()
            ) {
         Text(text = "Go to sign in button")
        }

    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenActivity(){
    SentryPayBankTheme() {
        HomeActivity()
    }
}
