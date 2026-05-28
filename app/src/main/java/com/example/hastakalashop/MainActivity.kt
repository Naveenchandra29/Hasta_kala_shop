package com.example.hastakalashop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Product(
    val name: String,
    val color: String,
    var stock: Int,
    val price: Int
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HastaKalaApp()
        }
    }
}

@Composable
fun HastaKalaApp() {

    var loggedIn by remember { mutableStateOf(false) }
    var artisanName by remember { mutableStateOf("") }

    if (!loggedIn) {

        LoginScreen(
            artisanName = artisanName,
            onNameChange = {
                artisanName = it
            },
            onLogin = {
                loggedIn = true
            }
        )

    } else {

        DashboardScreen(artisanName)
    }
}

@Composable
fun LoginScreen(
    artisanName: String,
    onNameChange: (String) -> Unit,
    onLogin: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF071A2F)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Hasta-Kala Shop",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Micro Sales Analytics Tool",
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = artisanName,
                onValueChange = onNameChange,
                label = {
                    Text("Enter Artisan Name")
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onLogin
            ) {

                Text("Open Dashboard")
            }
        }
    }
}

@Composable
fun DashboardScreen(
    artisanName: String
) {

    val products = remember {

        mutableStateListOf(

            Product("Banana Fiber Bag", "Red", 10, 250),
            Product("Banana Fiber Bag", "Blue", 2, 250),
            Product("Keychain", "Yellow", 15, 80),
            Product("Handmade Basket", "Brown", 5, 400),
            Product("Craft Box", "Green", 3, 180)
        )
    }

    var totalIncome by remember { mutableStateOf(0) }

    val salesMap = remember {
        mutableStateMapOf<String, Int>()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF071A2F)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Welcome, $artisanName 👋",
                color = Color.Cyan
            )

            Text(
                text = "Hasta-Kala Shop",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Smart Artisan Analytics",
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF132C4E)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "💰 Total Income",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "₹ $totalIncome",
                        color = Color.Green,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "This Week / This Month filter ready",
                        color = Color.LightGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "🛒 Quick Billing",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            products.forEach { product ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1B365D)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                Icons.Default.ShoppingBag,
                                contentDescription = null,
                                tint = Color.Yellow
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {

                                Text(
                                    text = product.name,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )

                                Text(
                                    text = "Color: ${product.color}",
                                    color = Color.Cyan
                                )

                                Text(
                                    text = "Stock: ${product.stock}",
                                    color =
                                        if (product.stock <= 2)
                                            Color.Red
                                        else
                                            Color.LightGray
                                )

                                Text(
                                    text = "Price: ₹${product.price}",
                                    color = Color.Green
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (product.stock <= 2) {

                            Text(
                                text = "⚠ Only ${product.stock} left — Time to make more",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        Button(
                            onClick = {

                                if (product.stock > 0) {

                                    product.stock--

                                    totalIncome += product.price

                                    val current =
                                        salesMap[product.color] ?: 0

                                    salesMap[product.color] =
                                        current + 1
                                }
                            }
                        ) {

                            Text("Save Sale")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF4A148C)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "📊 Best Seller Analytics",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    if (salesMap.isEmpty()) {

                        Text(
                            text = "No sales yet",
                            color = Color.LightGray
                        )

                    } else {

                        salesMap.forEach { (color, count) ->

                            Text(
                                text = "$color : $count sales",
                                color = Color.White,
                                fontSize = 18.sp
                            )

                            LinearProgressIndicator(
                                progress = {
                                    count / 10f
                                },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF00695C)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "🌍 Business Impact",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "• Reduces unsold dead stock",
                        color = Color.White
                    )

                    Text(
                        text = "• Helps artisans track business growth",
                        color = Color.White
                    )

                    Text(
                        text = "• Encourages smart production decisions",
                        color = Color.White
                    )

                    Text(
                        text = "• Gives analytics power to rural entrepreneurs",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}