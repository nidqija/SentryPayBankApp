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

// Placeholder data class for Contact
data class ContactUser(
    val id: String,
    val fullName: String,
    val username: String,
    val phoneNumber: String
)

@Composable
fun TransactionActivity(
    modifier: Modifier = Modifier,
    viewModel: TransactionLayerModel = viewModel()



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

    var searchQuery by remember { mutableStateOf("") }

    val IBMPlexSansFontFamily = FontFamily(
        Font(resId = R.font.ibmplexsans_medium, weight = FontWeight.Medium),
        Font(resId = R.font.ibmplexsans_regular, weight = FontWeight.Normal),
        Font(resId = R.font.ibmplexsans_semibold, weight = FontWeight.SemiBold)
    )

    // Placeholder list of contacts
    val placeholderContacts = remember {
        listOf(
            ContactUser("1", "John Doe", "@johndoe", "+1234567890"),
            ContactUser("2", "Jane Smith", "@janesmith", "+1987654321"),
            ContactUser("3", "Alex Rivera", "@alexr", "+1122334455"),
            ContactUser("4", "Sarah Connor", "@sconnor", "+1555444333"),
            ContactUser("5", "Michael Scott", "@prisonmike", "+1777888999")
        )
    }

    // Filter contacts based on search query
    val filteredContacts = placeholderContacts.filter { contact ->
        contact.fullName.contains(searchQuery, ignoreCase = true) ||
                contact.username.contains(searchQuery, ignoreCase = true) ||
                contact.phoneNumber.contains(searchQuery)
    }

    val userContactListState = viewModel?.userContactLists?.collectAsStateWithLifecycle()
    val userContactListWrapper = userContactListState?.value
    val realUserContactList = userContactListWrapper?.userDetails ?: emptyList()


    LaunchedEffect(viewModel ) {
        viewModel?.fetchUserList()
    }



    val filteredRealContacts = realUserContactList.filter {
        contact ->
        contact.userFullName.contains(searchQuery , ignoreCase = true) ||
                contact.userName.contains(searchQuery , ignoreCase = true) ||
                contact.userPhoneNumber.contains(searchQuery, ignoreCase = true)
    }

    val finalUIContacts = filteredRealContacts.map{detail ->
        ContactUser(
            id = detail.userId.toString(),
            fullName = detail.userFullName,
            username = detail.userName,
            phoneNumber = detail.userPhoneNumber
        )
    }




    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gxBankBackgroundGradient)
            .padding(12.dp)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
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
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontFamily = IBMPlexSansFontFamily
                ),
                placeholder = {
                    Text(
                        text = "Enter phone, username or fullname",
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

            Spacer(modifier = Modifier.height(24.dp))

            // Header for the Contacts List
            Text(
                text = "Contacts",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(alpha = 0.7f),
                fontFamily = IBMPlexSansFontFamily
            )

            Spacer(modifier = Modifier.height(12.dp))

            // LazyColumn to render the contact list dynamically
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                if(realUserContactList.isEmpty() && searchQuery.isEmpty()){
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = neonGreenAccent)
                        }
                    }
                } else {
                items(finalUIContacts, key = { it.id }) { contact ->
                    ContactRow(
                        contact = contact,
                        fontFamily = IBMPlexSansFontFamily,
                        cardBg = cardBackground,
                        accentColor = neonGreenAccent
                    )
                }
                }

                if (finalUIContacts.isEmpty() && realUserContactList.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No contacts found for ${searchQuery}",
                                color = Color.White.copy(alpha = 0.4f),
                                fontFamily = IBMPlexSansFontFamily
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContactRow(
    contact: ContactUser,
    fontFamily: FontFamily,
    cardBg: Color,
    accentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardBg, shape = RoundedCornerShape(16.dp))
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Initial/Avatar Placeholder
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(accentColor.copy(alpha = 0.15f), shape = CircleShape)
                .border(1.dp, accentColor.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = contact.fullName.take(1).uppercase(),
                color = accentColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                fontFamily = fontFamily
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Contact Details
        Column(
            modifier = Modifier.weight(1.0f)
        ) {
            Text(
                text = contact.fullName,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = fontFamily
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row {
                Text(
                    text = contact.username,
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 13.sp,
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "•",
                    color = Color.White.copy(alpha = 0.3f),
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = contact.phoneNumber,
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 13.sp,
                    fontFamily = fontFamily
                )
            }
        }
    }
}

@Preview
@Composable
fun TransactionActivityPreview() {
    SentryPayBankTheme {
        TransactionActivity()
    }
}
