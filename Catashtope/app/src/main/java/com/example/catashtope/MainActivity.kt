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
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.refreshData()
            delay(600_000L) // 10 minutes
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

                Button(onClick = { showDialog = true }) {
                    Text("Lihat Riwayat Cuaca")
                }

                if (showDialog) {
                    WeatherHistoryDialog(dataList = allWeather, onDismiss = { showDialog = false })
                }
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
fun WeatherHistoryDialog(dataList: List<WeatherResponse>, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Tutup")
            }
        },
        title = {
            Text("Riwayat Data Cuaca", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Box(modifier = Modifier.heightIn(max = 400.dp)) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(dataList) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = formatISO8601(item.current.time),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0D47A1)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("🌡️ Suhu: ${item.current.temperature} ${item.units.temperature}")
                                Text("🌧️ Hujan: ${item.current.rain} ${item.units.rain}")
                                Text("❄️ Salju: ${item.current.snowfall} ${item.units.snowfall}")
                                Text("🕒 Waktu: ${if (item.current.isDay == 1) "Siang" else "Malam"}")
                            }
                        }
                    }
                }
            }
        }
    )
}

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun formatISO8601(isoTime: String): String {
    return try {
        val utcParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        utcParser.timeZone = java.util.TimeZone.getTimeZone("UTC")

        val localFormatter = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("id", "ID"))
        localFormatter.timeZone = java.util.TimeZone.getDefault()

        val date = utcParser.parse(isoTime)
        localFormatter.format(date!!)
    } catch (e: Exception) {
        isoTime // fallback to raw if parsing fails
    }
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
