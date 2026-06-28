package com.project.fitnessstepscounter

import android.graphics.Color
import android.graphics.drawable.Icon
import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.carousel.Arrangement
import com.google.android.material.progressindicator.LinearProgressIndicator

@Composable
fun HomeScreen() {

    val progress = 0.85f

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(16.dp)
    ) {

        item {

            org.w3c.dom.Text(
                "Good Morning 👋",
                fontSize = 18.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            org.w3c.dom.Text(
                "Fitness Step Counter",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF4CAF50)
                ),
                shape = RoundedCornerShape(24.dp)
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        Icons.Default.DirectionsWalk,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(70.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    org.w3c.dom.Text(
                        "8,542",
                        color = Color.White,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold
                    )

                    org.w3c.dom.Text(
                        "Today's Steps",
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    org.w3c.dom.Text(
                        "85% of 10,000 Goal",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        item {

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                DashboardCard(
                    modifier = Modifier.weight(1f),
                    title = "Calories",
                    value = "325 kcal",
                    icon = Icons.Default.LocalFireDepartment,
                    color = Color(0xFFFF7043)
                )

                DashboardCard(
                    modifier = Modifier.weight(1f),
                    title = "Distance",
                    value = "6.25 km",
                    icon = Icons.Default.LocationOn,
                    color = Color(0xFF42A5F5)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                DashboardCard(
                    modifier = Modifier.weight(1f),
                    title = "Duration",
                    value = "1h 32m",
                    icon = Icons.Default.Timer,
                    color = Color(0xFFAB47BC)
                )

                DashboardCard(
                    modifier = Modifier.weight(1f),
                    title = "Streak",
                    value = "12 Days",
                    icon = Icons.Default.EmojiEvents,
                    color = Color(0xFFFFB300)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            org.w3c.dom.Text(
                "Today's Activity",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(20.dp)
            ) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    org.w3c.dom.Text(
                        "Step Chart Here",
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {

                Icon(Icons.Default.PlayArrow, null)

                Spacer(modifier = Modifier.width(8.dp))

                org.w3c.dom.Text(
                    "START TRACKING",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun DashboardCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    color: Color
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                icon,
                contentDescription = "",
                tint = color,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            org.w3c.dom.Text(
                value,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            org.w3c.dom.Text(
                title,
                color = Color.Gray
            )
        }
    }
}