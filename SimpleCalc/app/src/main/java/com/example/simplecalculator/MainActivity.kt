package com.example.simplecalculator

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var number1 by remember { mutableStateOf(TextFieldValue("")) }
    var number2 by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = number1,
                onValueChange = { number1 = it },
                label = { Text("Angka Pertama") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = number2,
                onValueChange = { number2 = it },
                label = { Text("Angka Kedua") },
                modifier = Modifier.fillMaxWidth()
            )

            // Baris 1: + -
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OperatorButton("+", number1.text, number2.text, context)
                OperatorButton("-", number1.text, number2.text, context)
            }

            // Baris 2: × ÷
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OperatorButton("×", number1.text, number2.text, context)
                OperatorButton("÷", number1.text, number2.text, context)
            }

            // Baris 3: %
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OperatorButton("%", number1.text, number2.text, context)
            }
        }
    }
}

@Composable
fun OperatorButton(
    symbol: String,
    num1: String,
    num2: String,
    context: android.content.Context
) {
    Button(onClick = {
        val result = calculate(num1, num2, symbol)
        val toast = Toast.makeText(context, "Hasil: $result", Toast.LENGTH_SHORT)
        val toastView = toast.view

        // Coba akses TextView bawaan Toast dan ubah ukuran huruf
        val text = toastView?.findViewById<TextView>(android.R.id.message)
        text?.textSize = 24f  // Ukuran besar (sp)

        toast.show()
    }) {
        Text(symbol)
    }
}


fun calculate(num1: String, num2: String, operator: String): String {
    return try {
        val n1 = num1.toDouble()
        val n2 = num2.toDouble()
        when (operator) {
            "+" -> (n1 + n2).toString()
            "-" -> (n1 - n2).toString()
            "×" -> (n1 * n2).toString()
            "÷" -> if (n2 != 0.0) (n1 / n2).toString() else "Tidak bisa dibagi 0"
            "%" -> if (n2 != 0.0) (n1 % n2).toString() else "Tidak bisa modulo 0"
            else -> "Operator tidak dikenali"
        }
    } catch (e: NumberFormatException) {
        "Input tidak valid"
    }
}

fun showCustomToast(context: android.content.Context, message: String) {
    val inflater = LayoutInflater.from(context)
    val layout = inflater.inflate(android.R.layout.simple_list_item_1, null)
    val text: TextView = layout.findViewById(android.R.id.text1)
    text.text = message
    text.textSize = 20f // Medium size

    val toast = Toast(context)
    toast.view = layout
    toast.duration = Toast.LENGTH_SHORT
    toast.setGravity(Gravity.CENTER, 0, 200)
    toast.show()
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    SimpleCalculatorTheme {
        CalculatorScreen()
    }
}
