package madproject.deepaks3533898.fitnessstepscounter.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import madproject.deepaks3533898.fitnessstepscounter.AppUserData
import madproject.deepaks3533898.fitnessstepscounter.Screen
import madproject.deepaks3533898.fitnessstepscounter.TesterData
import madproject.deepaks3533898.fitnessstepscounter.theme.C2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController
) {


    var testerData by remember {

        mutableStateOf(TesterData())

    }

    val context = LocalContext.current

    var loading by remember {

        mutableStateOf(true)

    }

    var showHeightDialog by remember {

        mutableStateOf(false)

    }

    var showWeightDialog by remember {

        mutableStateOf(false)

    }

    var editedHeight by remember {

        mutableStateOf("")

    }

    var editedWeight by remember {

        mutableStateOf("")

    }

    var showLogoutDialog by remember {

        mutableStateOf(false)

    }

//    editedHeight = testerData.height
//
//    showHeightDialog = true
//
//    editedWeight = testerData.weight
//
//    showWeightDialog = true


    LaunchedEffect(Unit) {

        val email = AppUserData.getUserEmail(context)

        if (email.isNotEmpty()) {

            getProfileDetails(

                email = email,

                onSuccess = {

                    testerData = it

                    loading = false

                },

                onFailure = {

                    loading = false

                    Toast.makeText(

                        context,

                        "Unable to load profile",

                        Toast.LENGTH_SHORT

                    ).show()

                }

            )

        } else {

            loading = false

        }

    }

//    Scaffold(
//
//        topBar = {
//
//            TopAppBar(
//
//                title = {
//
//                    Text(
//
//                        "My Profile",
//
//                        fontWeight = FontWeight.Bold
//
//                    )
//
//                }
//
//            )
//
//        }
//
//    ) { padding ->

    Column(

        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(20.dp)

    ) {

        ProfileAppbar()

        if (loading) {

            Box(

                modifier = Modifier
                    .fillMaxSize(),

                contentAlignment = Alignment.Center

            ) {

                CircularProgressIndicator()

            }

        } else {

            Column(

                modifier = Modifier
                    .fillMaxSize()

            ) {

                ProfileHeader(

                    testerData

                )

                Spacer(

                    modifier = Modifier.height(24.dp)

                )

                ProfileCard(

                    title = "Personal Information"

                ) {

                    ProfileItem(

                        icon = Icons.Default.Person,

                        title = "Name",

                        value = testerData.name

                    )

                    Divider()

                    ProfileItem(

                        icon = Icons.Default.Email,

                        title = "Email",

                        value = testerData.emailid

                    )

                    Divider()

                    ProfileItem(

                        icon = Icons.Default.Cake,

                        title = "Age",

                        value = "${testerData.age} Years"

                    )

                }

                Spacer(

                    modifier = Modifier.height(20.dp)

                )

                ProfileCard(

                    title = "Health Information"

                ) {

                    ProfileItem(

                        icon = Icons.Default.Height,

                        title = "Height",

                        value = "${testerData.height} cm",

                        editable = true,

                        onEdit = {

                            showHeightDialog = true

                        }

                    )

                    Divider()

                    ProfileItem(

                        icon = Icons.Default.MonitorWeight,

                        title = "Weight",

                        value = "${testerData.weight} kg",

                        editable = true,

                        onEdit = {

                            showWeightDialog = true

                        }

                    )

                }

                Spacer(

                    modifier = Modifier.height(28.dp)

                )

                Button(

                    onClick = {

                        showLogoutDialog = true

                        // Logout later

                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = C2
                    ),

                ) {

                    Icon(

                        Icons.Default.Logout,

                        null

                    )

                    Spacer(

                        Modifier.width(10.dp)

                    )

                    Text(

                        "Logout",

                        fontSize = 18.sp

                    )

                }

            }

        }

    }
//    }


    if (showLogoutDialog) {

        AlertDialog(

            onDismissRequest = {

                showLogoutDialog = false

            },

            icon = {

                Icon(

                    Icons.Default.Logout,

                    contentDescription = null,

                    tint = Color.Red

                )

            },

            title = {

                Text("Logout")

            },

            text = {

                Text(

                    "Are you sure you want to logout?"

                )

            },

            confirmButton = {

                TextButton(

                    onClick = {

//                        sessionManager.clearSession()

                        AppUserData.saveLoginStatus(context,false)

                        navController.navigate(Screen.Login.route) {

                            popUpTo(0)

                            launchSingleTop = true

                        }

                        showLogoutDialog = false

                    }

                ) {

                    Text(

                        "Logout",

                        color = Color.Red

                    )

                }

            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showLogoutDialog = false

                    }

                ) {

                    Text("Cancel")

                }

            }

        )

    }


    if (showHeightDialog) {

        AlertDialog(

            onDismissRequest = {

                showHeightDialog = false

            },

            title = {

                Text("Update Height")

            },

            text = {

                OutlinedTextField(

                    value = editedHeight,

                    onValueChange = {

                        editedHeight = it

                    },

                    label = {

                        Text("Height (cm)")

                    },

                    singleLine = true

                )

            },

            confirmButton = {

                TextButton(

                    onClick = {

                        updateHeightWeight(

                            email = testerData.emailid,

                            height = editedHeight,

                            weight = testerData.weight,

                            context = context

                        )

                        showHeightDialog = false

                    }

                ) {

                    Text("Save")

                }

            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showHeightDialog = false

                    }

                ) {

                    Text("Cancel")

                }

            }

        )

    }


    if (showWeightDialog) {

        AlertDialog(

            onDismissRequest = {

                showWeightDialog = false

            },

            title = {

                Text("Update Weight")

            },

            text = {

                OutlinedTextField(

                    value = editedWeight,

                    onValueChange = {

                        editedWeight = it

                    },

                    label = {

                        Text("Weight (kg)")

                    },

                    singleLine = true

                )

            },

            confirmButton = {

                TextButton(

                    onClick = {

                        updateHeightWeight(

                            email = testerData.emailid,

                            height = testerData.height,

                            weight = editedWeight,

                            context = context

                        )

                        showWeightDialog = false

                    }

                ) {

                    Text("Save")

                }

            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showWeightDialog = false

                    }

                ) {

                    Text("Cancel")

                }

            }

        )

    }

}


@Composable
fun ProfileAppbar() {

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


            Text(

                text = "User Profile",

                fontSize = 24.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(8.dp))



        }

    }

}

@Composable
fun ProfileHeader(

    testerData: TesterData

) {

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

            Box(

                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(

                        MaterialTheme.colorScheme.primaryContainer

                    ),

                contentAlignment = Alignment.Center

            ) {

                Icon(

                    Icons.Default.Person,

                    null,

                    modifier = Modifier.size(50.dp)

                )

            }

            Spacer(

                Modifier.height(14.dp)

            )

            Text(

                testerData.name,

                fontWeight = FontWeight.Bold,

                fontSize = 24.sp

            )

            Spacer(

                Modifier.height(4.dp)

            )

            Text(

                testerData.emailid,

                color = Color.Gray

            )

        }

    }

}

@Composable
fun ProfileCard(

    title: String,

    content: @Composable ColumnScope.() -> Unit

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp)

    ) {

        Column(

            modifier = Modifier.padding(18.dp)

        ) {

            Text(

                title,

                fontWeight = FontWeight.Bold,

                fontSize = 18.sp

            )

            Spacer(

                Modifier.height(16.dp)

            )

            content()

        }

    }

}

@Composable
fun ProfileItem(

    icon: ImageVector,

    title: String,

    value: String,

    editable: Boolean = false,

    onEdit: () -> Unit = {}

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),

        verticalAlignment = Alignment.CenterVertically

    ) {

        Icon(

            icon,

            null,

            tint = MaterialTheme.colorScheme.primary

        )

        Spacer(

            Modifier.width(16.dp)

        )

        Column(

            modifier = Modifier.weight(1f)

        ) {

            Text(

                title,

                color = Color.Gray,

                fontSize = 13.sp

            )

            Spacer(

                Modifier.height(2.dp)

            )

            Text(

                value,

                fontWeight = FontWeight.SemiBold,

                fontSize = 17.sp

            )

        }

        if (editable) {

            IconButton(

                onClick = onEdit

            ) {

                Icon(

                    Icons.Default.Edit,

                    null

                )

            }

        }

    }

}


fun getProfileDetails(

    email: String,

    onSuccess: (TesterData) -> Unit,

    onFailure: () -> Unit

) {

    val firebaseDatabase =
        FirebaseDatabase.getInstance()

    val databaseReference =
        firebaseDatabase
            .getReference("UsersData")

    databaseReference

        .child(email.replace(".", ","))

        .addValueEventListener(object : ValueEventListener {

            override fun onDataChange(

                snapshot: DataSnapshot

            ) {

                if (snapshot.exists()) {

                    val testerData =
                        snapshot.getValue(
                            TesterData::class.java
                        )

                    if (testerData != null) {

                        onSuccess(testerData)

                    } else {

                        onFailure()

                    }

                } else {

                    onFailure()

                }

            }

            override fun onCancelled(

                error: DatabaseError

            ) {

                onFailure()

            }

        })

}


fun updateHeightWeight(

    email: String,

    height: String,

    weight: String,

    context: Context

) {

    val firebaseDatabase =
        FirebaseDatabase.getInstance()

    val databaseReference =
        firebaseDatabase
            .getReference("UsersData")

    val updates = hashMapOf<String, Any>()

    updates["height"] = height
    updates["weight"] = weight

    databaseReference

        .child(email.replace(".", ","))

        .updateChildren(updates)

        .addOnSuccessListener {

            Toast.makeText(

                context,

                "Profile Updated Successfully",

                Toast.LENGTH_SHORT

            ).show()

        }

        .addOnFailureListener {

            Toast.makeText(

                context,

                "Failed to Update Profile",

                Toast.LENGTH_SHORT

            ).show()

        }

}