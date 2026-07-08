package madproject.deepaks3533898.fitnessstepscounter.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMIScreen() {

    var height by remember {
        mutableStateOf("")
    }

    var weight by remember {
        mutableStateOf("")
    }

    var bmi by remember {
        mutableFloatStateOf(0f)
    }

    var category by remember {
        mutableStateOf("")
    }

    var suggestion by remember {
        mutableStateOf("")
    }

    var healthyWeight by remember {
        mutableStateOf("")
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("BMI Calculator")

                }

            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {

            HeroSection()

            BMIGauge(

                bmi = bmi,

                category = category

            )

            InputCard(

                title = "Height",

                icon = Icons.Default.Settings

            ) {

                OutlinedTextField(

                    value = height,

                    onValueChange = {

                        height = it.filter { c ->
                            c.isDigit() || c == '.'
                        }

                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true,

                    label = {

                        Text("Height (cm)")

                    }

                )

            }

            InputCard(

                title = "Weight",

                icon = Icons.Default.Settings

            ) {

                OutlinedTextField(

                    value = weight,

                    onValueChange = {

                        weight = it.filter { c ->
                            c.isDigit() || c == '.'
                        }

                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true,

                    label = {

                        Text("Weight (kg)")

                    }

                )

            }

            Button(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                onClick = {

                    val h = height.toFloatOrNull()

                    val w = weight.toFloatOrNull()

                    if (h != null && w != null) {

                        val meter = h / 100

                        bmi = w / (meter * meter)

                        when {

                            bmi < 18.5f -> {

                                category = "Underweight"

                                suggestion =
                                    "Increase nutritious calories and strength training."

                            }

                            bmi < 25f -> {

                                category = "Normal"

                                suggestion =
                                    "Maintain your healthy lifestyle."

                            }

                            bmi < 30f -> {

                                category = "Overweight"

                                suggestion =
                                    "Exercise regularly and reduce calorie intake."

                            }

                            else -> {

                                category = "Obese"

                                suggestion =
                                    "Consult a healthcare professional and start a structured fitness plan."

                            }

                        }

                        val minWeight =
                            18.5f * meter * meter

                        val maxWeight =
                            24.9f * meter * meter

                        healthyWeight =
                            "${String.format("%.1f", minWeight)} kg - ${String.format("%.1f", maxWeight)} kg"

                    }

                }

            ) {

                Text(

                    "CALCULATE BMI",

                    fontSize = 18.sp

                )

            }

            if (bmi > 0f) {

                ResultCard(

                    bmi,

                    category,

                    healthyWeight,

                    suggestion

                )

            }

            OutlinedButton(

                modifier = Modifier.fillMaxWidth(),

                onClick = {

                    height = ""

                    weight = ""

                    bmi = 0f

                    category = ""

                    suggestion = ""

                    healthyWeight = ""

                }

            ) {

                Icon(

                    Icons.Default.Refresh,

                    null

                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("RESET")

            }

        }

    }

}

@Composable
fun HeroSection() {

    Card(

        modifier = Modifier.fillMaxWidth(),

        colors = CardDefaults.cardColors(

            containerColor = MaterialTheme.colorScheme.primaryContainer

        ),

        shape = RoundedCornerShape(24.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Box(

                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),

                contentAlignment = Alignment.Center

            ) {

                Icon(

                    Icons.Default.Favorite,

                    contentDescription = null,

                    tint = MaterialTheme.colorScheme.onPrimary,

                    modifier = Modifier.size(42.dp)

                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(

                text = "Know Your Body",

                fontSize = 26.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(

                text = "Calculate your Body Mass Index (BMI) to understand your health condition and receive personalized fitness suggestions.",

                style = MaterialTheme.typography.bodyMedium

            )

        }

    }

}

@Composable
fun InputCard(

    title: String,

    icon: androidx.compose.ui.graphics.vector.ImageVector,

    content: @Composable () -> Unit

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )

    ) {

        Column(

            modifier = Modifier.padding(20.dp)

        ) {

            Row(

                verticalAlignment = Alignment.CenterVertically

            ) {

                Box(

                    modifier = Modifier
                        .size(45.dp)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            CircleShape
                        ),

                    contentAlignment = Alignment.Center

                ) {

                    Icon(

                        imageVector = icon,

                        contentDescription = null,

                        tint = MaterialTheme.colorScheme.primary

                    )

                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(

                    text = title,

                    fontSize = 20.sp,

                    fontWeight = FontWeight.Bold

                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            content()

        }

    }

}

@Composable
fun ResultCard(

    bmi: Float,

    category: String,

    healthyWeight: String,

    suggestion: String

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        colors = CardDefaults.cardColors(

            containerColor = MaterialTheme.colorScheme.secondaryContainer

        ),

        shape = RoundedCornerShape(24.dp)

    ) {

        Column(

            modifier = Modifier.padding(24.dp)

        ) {

            Text(

                text = "BMI Result",

                fontSize = 22.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(

                text = String.format("%.1f", bmi),

                fontSize = 54.sp,

                fontWeight = FontWeight.ExtraBold,

                color = MaterialTheme.colorScheme.primary

            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(

                text = category,

                fontSize = 22.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(24.dp))

            Divider()

            Spacer(modifier = Modifier.height(20.dp))

            Text(

                text = "Healthy Weight Range",

                fontWeight = FontWeight.Bold,

                fontSize = 18.sp

            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(

                healthyWeight,

                style = MaterialTheme.typography.bodyLarge

            )

            Spacer(modifier = Modifier.height(20.dp))

            Divider()

            Spacer(modifier = Modifier.height(20.dp))

            Text(

                text = "Fitness Suggestion",

                fontWeight = FontWeight.Bold,

                fontSize = 18.sp

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(

                suggestion,

                style = MaterialTheme.typography.bodyLarge

            )

            Spacer(modifier = Modifier.height(24.dp))

            Divider()

            Spacer(modifier = Modifier.height(20.dp))

            Text(

                text = "Health Tips",

                fontWeight = FontWeight.Bold,

                fontSize = 18.sp

            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("💧 Drink at least 2–3 litres of water daily.")

            Spacer(modifier = Modifier.height(8.dp))

            Text("🥗 Eat a balanced diet rich in fruits and vegetables.")

            Spacer(modifier = Modifier.height(8.dp))

            Text("🏃 Exercise for at least 30 minutes every day.")

            Spacer(modifier = Modifier.height(8.dp))

            Text("😴 Get 7–8 hours of quality sleep every night.")

        }

    }

}


@Composable
fun BMIGauge(

    bmi: Float,

    category: String

) {

    val animatedBmi by animateFloatAsState(
        targetValue = bmi,
        label = "BMI Animation"
    )

    val primaryColor = MaterialTheme.colorScheme.primary


    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Text(

                text = "BMI Gauge",

                fontWeight = FontWeight.Bold,

                fontSize = 22.sp

            )

            Spacer(modifier = Modifier.height(20.dp))

            Canvas(

                modifier = Modifier
                    .size(280.dp)

            ) {

                val stroke = 28f

                val diameter = size.minDimension - stroke

                val topLeft = Offset(

                    (size.width - diameter) / 2,

                    (size.height - diameter) / 2

                )

                val arcSize = Size(diameter, diameter)

                drawArc(

                    color = Color(0xFF42A5F5),

                    startAngle = 180f,

                    sweepAngle = 45f,

                    useCenter = false,

                    topLeft = topLeft,

                    size = arcSize,

                    style = Stroke(
                        width = stroke,
                        cap = StrokeCap.Round
                    )

                )

                drawArc(

                    color = Color(0xFF4CAF50),

                    startAngle = 225f,

                    sweepAngle = 45f,

                    useCenter = false,

                    topLeft = topLeft,

                    size = arcSize,

                    style = Stroke(
                        width = stroke,
                        cap = StrokeCap.Round
                    )

                )

                drawArc(

                    color = Color(0xFFFFC107),

                    startAngle = 270f,

                    sweepAngle = 45f,

                    useCenter = false,

                    topLeft = topLeft,

                    size = arcSize,

                    style = Stroke(
                        width = stroke,
                        cap = StrokeCap.Round
                    )

                )

                drawArc(

                    color = Color(0xFFF44336),

                    startAngle = 315f,

                    sweepAngle = 45f,

                    useCenter = false,

                    topLeft = topLeft,

                    size = arcSize,

                    style = Stroke(
                        width = stroke,
                        cap = StrokeCap.Round
                    )

                )

                val maxBMI = 40f

                val clamped = animatedBmi.coerceIn(0f, maxBMI)

                val angle = 180f + (clamped / maxBMI) * 180f

                val radius = diameter / 2

                val center = Offset(

                    size.width / 2,

                    size.height / 2

                )

                val radians = Math.toRadians(angle.toDouble())

                val needleLength = radius - 25

                val needleEnd = Offset(

                    x = center.x + needleLength * cos(radians).toFloat(),

                    y = center.y + needleLength * sin(radians).toFloat()

                )

                drawLine(

                    color = primaryColor,

                    start = center,

                    end = needleEnd,

                    strokeWidth = 8f

                )

                drawCircle(

                    color = primaryColor,

                    radius = 12f,

                    center = center

                )

            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(

                text = String.format("%.1f", animatedBmi),

                fontSize = 46.sp,

                fontWeight = FontWeight.Bold,

                color = MaterialTheme.colorScheme.primary

            )

            Spacer(modifier = Modifier.height(8.dp))

            if (category.isNotEmpty()) {

                AssistChip(

                    onClick = {},

                    label = {

                        Text(category)

                    }

                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                GaugeLegend(

                    color = Color(0xFF42A5F5),

                    text = "Under"

                )

                GaugeLegend(

                    color = Color(0xFF4CAF50),

                    text = "Normal"

                )

                GaugeLegend(

                    color = Color(0xFFFFC107),

                    text = "Over"

                )

                GaugeLegend(

                    color = Color(0xFFF44336),

                    text = "Obese"

                )

            }

        }

    }

}

@Composable
fun GaugeLegend(

    color: Color,

    text: String

) {

    Row(

        verticalAlignment = Alignment.CenterVertically

    ) {

        Box(

            modifier = Modifier
                .size(14.dp)
                .background(
                    color,
                    CircleShape
                )

        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(

            text = text,

            fontSize = 12.sp

        )

    }

}