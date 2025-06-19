package com.example.cakeclicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cakeclicker.data.Source
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CakeClickerTheme {
                CakeClickerApp()
            }
        }
    }
}

@Composable
fun CakeClickerApp() {
    var revenue by remember { mutableStateOf(0) }
    var dessertsSold by remember { mutableStateOf(0) }
    var currentCake by remember { mutableStateOf(Source.cakes.first()) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Today's Special
        Text(
            text = "Today's Special",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = currentCake.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Dessert Image
        Image(
            painter = painterResource(id = currentCake.imageRes),
            contentDescription = currentCake.name,
            modifier = Modifier
                .size(200.dp)
                .clickable {
                    revenue += currentCake.price
                    dessertsSold++
                    currentCake = Source.cakes.last { dessertsSold >= it.startProductionAmount }
                }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Stats
        Text("Desserts sold", style = MaterialTheme.typography.bodyLarge)
        Text(
            dessertsSold.toString(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text("Total Revenue", style = MaterialTheme.typography.bodyLarge)
        Text(
            NumberFormat.getCurrencyInstance().format(revenue),
            style = MaterialTheme.typography.headlineMedium
        )

        // Share Button
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { shareResults(context, dessertsSold, revenue) },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Share My Results")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CakeClickerPreview() {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6),
            background = Color(0xFFFFFBFE)
        )
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Preview Content
                Text(
                    text = "Today's Special",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Cupcake",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.cupcake), // Replace with your preview drawable
                    contentDescription = "Preview dessert",
                    modifier = Modifier.size(200.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text("Desserts sold", style = MaterialTheme.typography.bodyLarge)
                Text(
                    "42",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text("Total Revenue", style = MaterialTheme.typography.bodyLarge)
                Text(
                    "$210",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text("Share My Results")
                }
            }
        }
    }
}

private fun shareResults(context: Context, dessertsSold: Int, revenue: Int) {
    val shareText = "I've sold $dessertsSold desserts and earned ${NumberFormat.getCurrencyInstance().format(revenue)} in Cake Clicker!"
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}

@Composable
fun CakeClickerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6)
        ),
        content = content
    )
}