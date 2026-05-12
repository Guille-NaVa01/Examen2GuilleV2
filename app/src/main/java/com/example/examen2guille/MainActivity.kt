package com.example.examen2guille

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen2guille.ui.theme.Examen2GuilleTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Examen2GuilleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GuessingGameScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GuessingGameScreen(modifier: Modifier = Modifier) {
    // Número aleatorio generado una sola vez al iniciar
    val randomNumber = remember { Random.nextInt(0, 101) }

    var inputText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var resultMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Adivina el número",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ingresa un número entre 0 y 100",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = { newValue ->
                // Solo permitir dígitos numéricos
                if (newValue.all { it.isDigit() }) {
                    inputText = newValue
                }
            },
            label = { Text("Tu número") },
            placeholder = { Text("Ej: 42") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val userNumber = inputText.toIntOrNull()
                resultMessage = when {
                    userNumber == null -> "Por favor ingresa un número válido."
                    userNumber < randomNumber -> "📉 Tu número ($userNumber) es MENOR que el número secreto."
                    userNumber > randomNumber -> "📈 Tu número ($userNumber) es MAYOR que el número secreto."
                    else -> "🎉 ¡CORRECTO! Adivinaste el número ($userNumber)."
                }
                showDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(text = "Verificar", fontSize = 16.sp)
        }
    }

    // Dialog de resultado
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Resultado",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(text = resultMessage)
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Aceptar")
                }
            }
        )
    }
}