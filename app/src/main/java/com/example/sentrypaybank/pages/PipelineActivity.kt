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
import com.example.sentrypaybank.R
import com.example.sentrypaybank.ui.theme.SentryPayBankTheme
import java.util.Locale



// create a data class to store the query from the backend
// when creating a data class , it is a final class by default
// it means that it cannot be redeclared in another file if the files are in the same working dir

data class ServicesItem(
    val serviceName : String,
    val serviceDesc : String,
    val servicePrice : Double,
    val serviceType : String,
    val renewalPeriod : String,
    val currency : String
)


@Composable
fun PipelineActivity(

    modifier : Modifier = Modifier,
    onNavigateToHome: () -> Unit = {}


){
    val neonGreenAccent = Color(0xFF00E676)
    val cardBackground = Color(0xFF1F2937).copy(alpha = 0.4f)
    val navBarDarkBackground = Color(0xFF0B0F19) // Balanced premium dark mode backdrop match
    val gxBankBackgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0B0F19),
            Color(0xFF111827),
            Color(0xFF1F2937)
        )
    )

    val navItems = listOf(
        NavItem("Home"),
        NavItem("Pipelines"),
        NavItem("Payment"),
        NavItem("Profile")
    )

    var selectedItem by remember { mutableStateOf(1) };

    val IBMPlexSansFontFamily = FontFamily(
        Font(resId = R.font.ibmplexsans_medium, weight = FontWeight.Medium),
        Font(resId = R.font.ibmplexsans_regular, weight = FontWeight.Normal),
        Font(resId = R.font.ibmplexsans_semibold, weight = FontWeight.SemiBold)
    )



        Column(
            modifier = modifier
                .fillMaxSize()
                .background(gxBankBackgroundGradient)
                .padding(horizontal = 12.dp)


        ) {

                Column(
                    modifier = Modifier.padding(20.dp)

                ){


                    Text(
                        text = "Pipeline Marketplace",
                        color = Color.White,
                        fontFamily = IBMPlexSansFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,

                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    Text(
                        text = "Check out our services below , all in one place!",
                        color = Color.White.copy(alpha = 0.5f),
                        fontFamily = IBMPlexSansFontFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,

                        )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            brush = Brush.linearGradient(listOf(Color.White.copy(alpha = 0.15f), Color.Transparent)),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(16.dp)
                ){

                        Column(
                            modifier = Modifier.padding(12.dp)
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "Netflix Premium",
                                color = Color.Black,
                                fontSize = 20.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Enjoy netflix premium and global account sharing amongst your family members!",
                                color = Color.Black,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp

                            )
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