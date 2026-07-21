    package com.example.sentrypaybank.pages

    import androidx.compose.foundation.background
    import androidx.compose.foundation.border
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.*
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
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
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.example.sentrypaybank.R
    import com.example.sentrypaybank.backend.remote.data.viewmodel.ServiceLayerState
    import com.example.sentrypaybank.backend.remote.data.viewmodel.ServiceViewModel
    import com.example.sentrypaybank.ui.theme.SentryPayBankTheme
    import com.google.gson.annotations.SerializedName

    import java.util.Locale

    data class ServicesItem(

        @SerializedName("servicesId") val servicesId: String,
        @SerializedName("serviceName") val serviceName: String,
        @SerializedName("serviceDescription") val serviceDesc: String,
        @SerializedName("servicePrice") val servicePrice: Double,
        @SerializedName("serviceType") val serviceType: String,
        @SerializedName("renewalPeriod") val renewalPeriod: String,
        @SerializedName("currency") val currency: String
    )

    @Composable
    fun PipelineActivity(
        modifier : Modifier = Modifier,
        onNavigateToHome: () -> Unit = {},
        viewModel: ServiceViewModel = viewModel(),
        onNavigateToPipelineById : (serviceId : String) -> Unit = {}
    ){
        val uiState by viewModel.uiState.collectAsState()
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

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(gxBankBackgroundGradient)
                .padding(horizontal = 16.dp) // Slightly adjusted for standard alignment
        ) {
            Column(
                modifier = Modifier.padding(top = 24.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ){
                Text(
                    text = "Pipeline Marketplace",
                    color = Color.White,
                    fontFamily = IBMPlexSansFontFamily,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Check out our services below , all in one place!",
                    color = Color.White.copy(alpha = 0.5f),
                    fontFamily = IBMPlexSansFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

          when(val state = uiState){
              is ServiceLayerState.Loading -> {
                  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                      CircularProgressIndicator(color = neonGreenAccent)
              }
          }
          is ServiceLayerState.Error ->{
              Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                  Column(horizontalAlignment = Alignment.CenterHorizontally) {
                      Text(text = state.message, color = Color.Red, fontFamily = IBMPlexSansFontFamily)
                      Spacer(modifier = Modifier.height(8.dp))
                      Button(onClick = { viewModel.getServices() }) {
                          Text("Retry")
                      }
                  }
              }
          }

          is ServiceLayerState.Success -> {
              val servicesList = state.data.services

              LazyColumn(
                  verticalArrangement = Arrangement.spacedBy(16.dp),
                  contentPadding = PaddingValues(bottom = 24.dp)
              ) {
                  items(servicesList) {

                   item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(20.dp))
                    // Combines dark base with your alpha cardBackground to make it drop cleanly over the gradient
                    .background(navBarDarkBackground.copy(alpha = 0.6f))
                    .background(cardBackground)
                    .clickable{onNavigateToPipelineById(item.servicesId)}
                    .border(
                        width = 1.2.dp,
                        brush = Brush.linearGradient(
                            listOf(Color.White.copy(alpha = 0.15f), Color.Transparent)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(16.dp)
            ){
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                ){
                    // Title uses clean White for core readability
                    Text(
                        text = item.serviceName,
                        color = Color.White,
                        fontFamily = IBMPlexSansFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Description uses soft muted white to balance layout text hierarchy
                    Text(
                        text = item.serviceDesc,
                        color = Color.White.copy(alpha = 0.65f),
                        fontFamily = IBMPlexSansFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Improvised placement: Uses your neonGreenAccent strategically for the actionable indicator/price point
                    Row(
                        modifier = Modifier.padding(1.dp)
                    ){
                        Text(
                            text = "Price : ${item.servicePrice} ${item.currency}",
                            color = neonGreenAccent,
                            fontFamily = IBMPlexSansFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .background(neonGreenAccent.copy(alpha = 0.1f), shape = RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )

                        Spacer(modifier = Modifier.padding(12.dp) )

                        Text(
                            text = item.renewalPeriod,
                            color = neonGreenAccent,
                            fontFamily = IBMPlexSansFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .background(neonGreenAccent.copy(alpha = 0.1f), shape = RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
             }
                  }
              }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PipelineActivityPreview(){
        SentryPayBankTheme{
            PipelineActivity()
        }
    }

