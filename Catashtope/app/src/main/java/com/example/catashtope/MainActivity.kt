package com.example.catashtope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catashtope.ui.theme.CatashtopeTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatashtopeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen(weatherViewModel)
                }
            }
        }
    }
}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val state by viewModel.uiState.collectAsState()
    val allWeather by viewModel.allWeatherData.collectAsState()

    // Polling untuk refresh data secara periodik
    LaunchedEffect(Unit) {
        while (true) {
            viewModel.refreshData()
            delay(600_000L) // Refresh setiap 10 menit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            state.weatherData != null -> {
                WeatherCard(
                    data = state.weatherData!!,
                    lastUpdated = state.lastUpdated,
                    onRefresh = { viewModel.refreshData() }
                )
                Spacer(modifier = Modifier.height(24.dp))
                WeatherHistoryTable(dataList = allWeather)
            }
            state.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                ErrorView(message = state.error!!, onRetry = { viewModel.refreshData() })
            }
        }
    }
}

@Composable
fun WeatherCard(data: WeatherResponse, lastUpdated: Long?, onRefresh: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Cuaca Saat Ini di Surabaya", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${data.current.temperature} ${data.units.temperature}",
                fontSize = 48.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onRefresh) {
                Text(text = "Segarkan Data")
            }
            Spacer(modifier = Modifier.height(12.dp))
            if (lastUpdated != null) {
                Text(
                    text = "Data terakhir diambil: ${formatTimestamp(lastUpdated)}",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun WeatherHistoryTable(dataList: List<WeatherResponse>) {
    Text(
        text = "Riwayat Data Cuaca",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), // Use height directly here
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dataList) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("ID: ${item.id}", fontWeight = FontWeight.SemiBold)
                    Text("Waktu: ${item.current.time}")
                    Text("Suhu: ${item.current.temperature} ${item.units.temperature}")
                    Text("Hujan: ${item.current.rain} ${item.units.rain}")
                    Text("Salju: ${item.current.snowfall} ${item.units.snowfall}")
                    Text("Siang/Malam: ${if (item.current.isDay == 1) "Siang" else "Malam"}")
                }
            }
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Terjadi kesalahan:\n$message",
            color = Color.Red,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Coba Lagi")
        }
    }
}
