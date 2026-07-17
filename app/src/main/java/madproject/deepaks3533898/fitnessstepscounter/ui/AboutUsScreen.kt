package madproject.deepaks3533898.fitnessstepscounter.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
    navController: NavHostController
) {

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(

                        "About Us",

                        fontWeight = FontWeight.Bold

                    )

                },
                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )

                    }

                }

            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp)

        ) {

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(

                    containerColor = MaterialTheme.colorScheme.primaryContainer

                )

            ) {

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),

                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Icon(

                        Icons.Default.DirectionsWalk,

                        contentDescription = null,

                        modifier = Modifier.size(60.dp),

                        tint = Color.Red

                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(

                        "Fitness Step Counter",

                        fontSize = 28.sp,

                        fontWeight = FontWeight.Bold

                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(

                        text = "Fitness Step Counter helps users monitor daily physical activities by tracking steps, calories, walking distance, activity duration, BMI, goals, reminders, and fitness statistics.",

                        textAlign = TextAlign.Center,

                        color = Color.DarkGray

                    )

                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            InfoCard(

                title = "🚀 App Features"

            ) {

                FeatureItem("👣", "Live Step Counter")
                FeatureItem("🔥", "Calorie Tracking")
                FeatureItem("📏", "Distance Measurement")
                FeatureItem("⏱", "Activity Duration")
                FeatureItem("🎯", "Daily Goals")
                FeatureItem("💧", "Water Intake")
                FeatureItem("⚖", "BMI Calculator")
                FeatureItem("📊", "Weekly & Monthly Statistics")
                FeatureItem("👤", "Profile Management")

            }

            Spacer(modifier = Modifier.height(20.dp))

            InfoCard(

                title = "📋 Project Details"

            ) {

                DetailRow(

                    "Project Name",

                    "Fitness Step Counter"

                )

                Divider()

                DetailRow(

                    "Student ID",

                    "S3533898"

                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            InfoCard(

                title = "📧 Contact Us"

            ) {

                Text(

                    "Thank you for using Fitness Step Counter.",

                    fontWeight = FontWeight.SemiBold

                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(

                    "If you experience any issues or have suggestions for improving the application, please feel free to contact us."

                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Icon(

                        Icons.Default.Email,

                        contentDescription = null,

                        tint = MaterialTheme.colorScheme.primary

                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(

                        "deepaksepuk@gmail.com",

                        fontWeight = FontWeight.Medium

                    )

                }

            }

            Spacer(modifier = Modifier.height(24.dp))

        }

    }

}


@Composable
fun InfoCard(

    title: String,

    content: @Composable ColumnScope.() -> Unit

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp)

    ) {

        Column(

            modifier = Modifier.padding(20.dp)

        ) {

            Text(

                title,

                fontSize = 20.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(16.dp))

            content()

        }

    }

}


@Composable
fun FeatureItem(

    emoji: String,

    title: String

) {

    Row(

        modifier = Modifier.padding(vertical = 6.dp),

        verticalAlignment = Alignment.CenterVertically

    ) {

        Text(

            emoji,

            fontSize = 20.sp

        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(

            title,

            fontSize = 16.sp

        )

    }

}

@Composable
fun DetailRow(

    title: String,

    value: String

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),

        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        Text(

            title,

            color = Color.Gray

        )

        Text(

            value,

            fontWeight = FontWeight.SemiBold

        )

    }

}