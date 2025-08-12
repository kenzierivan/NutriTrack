package com.fit2081.kenzierivanwiguna_34726896_a1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit2081.kenzierivanwiguna_34726896_a1.ui.theme.KenzieRivanWiguna_34726896_A1Theme

class ClinicianLogin : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val currentScreen = "Settings"
            KenzieRivanWiguna_34726896_A1Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(currentScreen) }) { innerPadding ->
                    ClinicianLogin(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ClinicianLogin(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var keySelected by remember { mutableStateOf("") }
    val clinicianKey by remember { mutableStateOf("dollar-entry-apples") }
    Column(
        modifier = Modifier.padding(24.dp,36.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Clinician Login",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = keySelected,
                onValueChange = { keySelected = it },
                label = {
                    Text(
                        text = "Clinician Key",
                        fontWeight = FontWeight.Bold
                    )
                },
                placeholder = { Text("Enter your clinician key") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(30)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (keySelected == clinicianKey) {
                        val intent = Intent(context, ClinicianDashboard::class.java)
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Invalid Clinician Key", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(8.dp).height(60.dp),
                shape = RoundedCornerShape(25),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text(
                    text = "Clinician Login",
                    fontSize = 18.sp
                )
            }
        }

    }
}

