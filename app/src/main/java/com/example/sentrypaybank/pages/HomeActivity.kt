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
// make a function to initialize the main function for HomeActivity Page
fun HomeActivity(
    modifier: Modifier = Modifier,

    // lambda function to init the redirection of user from home page to sign in page
    onNavigateToSignIn : () -> Unit = {}

){
    // root layout arranged vertically , centered on the screen
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            // make the entire screen area to be clickable
            .clickable{onNavigateToSignIn()},
        // centers the content horizontally
        horizontalAlignment = Alignment.CenterHorizontally,
        // centers the content vertically
        verticalArrangement = Arrangement.Center
    ){
        // add text and style of the text
        Text(
            text = "This is a home page",
            style = MaterialTheme.typography.headlineMedium

        )
        // add spacer in between of the components
        Spacer(modifier = Modifier.padding(20.dp))

        // add button and call the lambda function
        Button(onClick = {onNavigateToSignIn()} ,
            // stretch the button to fill the max width of screen
            modifier = Modifier.fillMaxWidth()
            ) {

            // add text in the button area
         Text(text = "Go to sign in button")
        }

    }

}

// allow the page to preview when designing the ui

@Preview(showBackground = true)
@Composable
fun HomeScreenActivity(){
    // wrap the home page with sentrypaybank theme ( Material 3 )
    SentryPayBankTheme() {
        HomeActivity()
    }
}
