package com.example.loginpage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LoginPage() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(false) }

    var loggedInUser by remember { mutableStateOf("") }
    var loggedInPass by remember { mutableStateOf("") }

    if (isLoggedIn) {
        HomePage(loggedInUser)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Login", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (username == "admin" && password == "admin123") {
                        // Simpan untuk ditampilkan di halaman berikutnya
                        loggedInUser = username
                        loggedInPass = password
                        showDialog = true

                        // Format waktu sekarang
                        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        val currentTime = formatter.format(Date())

                        // Cetak ke Logcat
                        Log.d("LoginCredential", "Login at $currentTime | Username: $username | Password: $password")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("google", "facebook", "twitter").forEach { name ->
                    Image(
                        painter = painterResource(
                            id = when (name) {
                                "google" -> R.drawable.google
                                "facebook" -> R.drawable.facebook
                                "twitter" -> R.drawable.twitter
                                else -> R.drawable.google
                            }
                        ),
                        contentDescription = name,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { }
                    )
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Berhasil Login") },
                    text = { Text("Selamat datang, $username!") },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            isLoggedIn = true
                        }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}
