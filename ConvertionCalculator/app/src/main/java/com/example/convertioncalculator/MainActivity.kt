package com.example.convertioncalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.convertioncalculator.ui.theme.ConvertionCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConvertionCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrencyConverterScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CurrencyConverterScreen(modifier: Modifier = Modifier) {
    val currencies = listOf("USD", "IDR", "EUR", "JPY")
    val exchangeRatesToUSD = mapOf(
        "USD" to 1.0,
        "IDR" to 1 / 16000.0,
        "EUR" to 1 / 0.91,
        "JPY" to 1 / 157.4
    )

    var amountInput by remember { mutableStateOf("") }
    var sourceCurrency by remember { mutableStateOf("USD") }
    var targetCurrency by remember { mutableStateOf("IDR") }

    val convertedAmount = amountInput.toDoubleOrNull()?.let {
        val amountInUSD = it * (exchangeRatesToUSD[sourceCurrency] ?: 1.0)
        val rateToTarget = 1 / (exchangeRatesToUSD[targetCurrency] ?: 1.0)
        amountInUSD * rateToTarget
    } ?: 0.0

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Currency Converter", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Amount") },
            singleLine = true
        )

        Column {
            Text("Select source currency (from):", style = MaterialTheme.typography.bodySmall)
            CurrencyDropdown(
                selectedCurrency = sourceCurrency,
                onCurrencySelected = { sourceCurrency = it },
                currencies = currencies
            )
        }

        Column {
            Text("Select target currency (to):", style = MaterialTheme.typography.bodySmall)
            CurrencyDropdown(
                selectedCurrency = targetCurrency,
                onCurrencySelected = { targetCurrency = it },
                currencies = currencies
            )
        }

        Text(
            text = "Converted: $convertedAmount $targetCurrency",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun CurrencyDropdown(
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit,
    currencies: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedCurrency)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(currency) },
                    onClick = {
                        onCurrencySelected(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyConverterPreview() {
    ConvertionCalculatorTheme {
        CurrencyConverterScreen()
    }
}
